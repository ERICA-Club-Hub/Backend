package kr.hanjari.backend.domain.club.application.query.impl;

import java.util.ArrayList;
import java.util.List;
import kr.hanjari.backend.domain.club.application.command.ClubCommandService;
import kr.hanjari.backend.domain.club.application.query.ClubQueryService;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.ClubRegistration;
import kr.hanjari.backend.domain.club.domain.entity.detail.ClubInstagramImage;
import kr.hanjari.backend.domain.club.domain.entity.detail.Introduction;
import kr.hanjari.backend.domain.club.domain.entity.detail.Recruitment;
import kr.hanjari.backend.domain.club.domain.entity.detail.Schedule;
import kr.hanjari.backend.domain.club.domain.entity.draft.ClubDetailDraft;
import kr.hanjari.backend.domain.club.domain.entity.draft.IntroductionDraft;
import kr.hanjari.backend.domain.club.domain.entity.draft.RecruitmentDraft;
import kr.hanjari.backend.domain.club.domain.entity.draft.ScheduleDescriptionDraft;
import kr.hanjari.backend.domain.club.domain.entity.draft.ScheduleDraft;
import kr.hanjari.backend.domain.club.domain.enums.*;
import kr.hanjari.backend.domain.club.domain.repository.ClubRegistrationRepository;
import kr.hanjari.backend.domain.club.domain.repository.ClubRepository;
import kr.hanjari.backend.domain.club.domain.repository.ClubInstagramImageRepository;
import kr.hanjari.backend.domain.club.domain.repository.detail.IntroductionRepository;
import kr.hanjari.backend.domain.club.domain.repository.detail.RecruitmentRepository;
import kr.hanjari.backend.domain.club.domain.repository.detail.ScheduleRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.ClubDetailDraftRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.IntroductionDraftRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.RecruitmentDraftRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.ScheduleDescriptionDraftRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.ScheduleDraftRepository;
import kr.hanjari.backend.domain.club.domain.repository.search.ClubSearchRepository;
import kr.hanjari.backend.domain.club.domain.repository.search.ClubSpecifications;
import kr.hanjari.backend.domain.club.presentation.dto.response.*;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubSearchResponse.ClubSearchResult;
import kr.hanjari.backend.domain.club.presentation.dto.response.GetInstagrams.ClubInstagramDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubBasicInfoResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubDetailDraftResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubIntroductionDraftResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubRecruitmentDraftResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubScheduleDraftResponse;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import kr.hanjari.backend.infrastructure.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubQueryServiceImpl implements ClubQueryService {

    public static final int FIRST_PAGE = 0;
    public static final int MAIN_PAGE_OFFSET = 3;
    public static final int MAIN_INSTAGRAM_OFFSET = 3;

    public static final String INSTAGRAM_URL = "https://www.instagram.com/";

    private final ClubRepository clubRepository;
    private final ClubRegistrationRepository clubRegistrationRepository;
    private final IntroductionRepository introductionRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ScheduleRepository scheduleRepository;
    private final ClubInstagramImageRepository clubInstagramImageRepository;

    private final IntroductionDraftRepository introductionDraftRepository;
    private final RecruitmentDraftRepository recruitmentDraftRepository;
    private final ClubDetailDraftRepository clubDetailDraftRepository;
    private final ScheduleDraftRepository scheduleDraftRepository;
    private final ScheduleDescriptionDraftRepository scheduleDescriptionDraftRepository;

    private final ClubSearchRepository clubSearchRepository;

    private final S3Service s3Service;
    private final ClubCommandService clubCommandService;

    @Override
    public ClubIdResponse getAllClubIds() {
        List<Long> clubIds = clubRepository.findAll().stream()
                .map(Club::getId)
                .toList();
        return ClubIdResponse.of(clubIds, clubIds.size());
    }

    @Override
    public GetRegistrationsResponse getRegistrations(int page, int size) {
        Page<ClubRegistration> clubRegistrationList = clubRegistrationRepository.findAllByCommand(
                PageRequest.of(page, size), Command.REGISTER
        );

        List<ClubRegistrationResponse> clubRegistrationResponseDTOList = clubRegistrationList.stream()
                .map(clubRegistration -> ClubRegistrationResponse.of(
                        clubRegistration.getId(),
                        clubRegistration.getName(),
                        clubRegistration.getBriefIntroduction(),
                        clubRegistration.getCategoryInfo(),
                        resolveImageUrl(clubRegistration)
                ))
                .toList();

        return GetRegistrationsResponse.of(clubRegistrationResponseDTOList);
    }

    @Override
    public ClubDetailResponse findClubDetail(Long clubId) {
        Club club = getClub(clubId);
        clubCommandService.incrementClubViewCount(clubId);

        return ClubDetailResponse.of(
            club.getId(),
            club.getDescription(),
            club.getLeaderName(),
            club.getLeaderPhone(),
            club.getLeaderEmail(),
            club.getMembershipFee(),
            club.getSnsUrl(),
            club.getApplicationUrl());
    }

    @Override
    public ClubOverviewResponse findClubOverview(Long clubId) {
        Club club = getClub(clubId);
        return ClubOverviewResponse.of(club, s3Service.getDownloadUrl(club.getImageFile().getId()));
    }

    @Override
    public ClubBasicInfoResponse findClubBasicInfo(Long clubId) {
        Club club = getClub(clubId);
        return ClubBasicInfoResponse.of(club);
    }

    @Override
    public ClubDetailDraftResponse findClubDetailDraft(Long clubId) {
        Club club = getClub(clubId);
        ClubDetailDraft clubDetailDraft = getClubDetailDraft(clubId);

        return ClubDetailDraftResponse.of(clubDetailDraft, club);
    }

    @Override
    public ClubScheduleResponse findAllClubActivities(Long clubId) {
        validateIsClubExistsById(clubId);
        Club club = getClub(clubId);
        List<Schedule> schedules = scheduleRepository.findAllByClubId(clubId);

        return ClubScheduleResponse.of(schedules, club);
    }

    @Override
    public ClubScheduleDraftResponse findAllClubActivitiesDraft(Long clubId) {
        validateIsClubExistsById(clubId);
        ScheduleDescriptionDraft scheduleDescriptionDraft = getScheduleDescriptionDraft(clubId);
        List<ScheduleDraft> schedules = scheduleDraftRepository.findAllByClubIdOrderByMonth(clubId);

        return ClubScheduleDraftResponse.of(schedules, scheduleDescriptionDraft.getDescription());
    }


    @Override
    public ClubIntroductionResponse findClubIntroduction(Long clubId) {
        validateIsClubExistsById(clubId);
        Introduction introduction = getIntroductionOrElseNull(clubId);

        return ClubIntroductionResponse.of(introduction);
    }

    @Override
    public ClubIntroductionDraftResponse findClubIntroductionDraft(Long clubId) {
        Club club = getClub(clubId);
        IntroductionDraft introduction = getIntroduction(clubId);

        return ClubIntroductionDraftResponse.of(club, introduction,
                s3Service.getDownloadUrl(club.getImageFile().getId()));
    }

    @Override
    public ClubRecruitmentResponse findClubRecruitment(Long clubId) {
        Recruitment recruitment = getRecruitmentOrElseNull(clubId);
        return ClubRecruitmentResponse.of(recruitment);
    }

    @Override
    public ClubRecruitmentDraftResponse findClubRecruitmentDraft(Long clubId) {
        Club club = getClub(clubId);
        RecruitmentDraft recruitment = getRecruitment(clubId);

        return ClubRecruitmentDraftResponse.of(club, recruitment,
                s3Service.getDownloadUrl(club.getImageFile().getId()));
    }

    @Override
    public ClubSearchResponse findCentralClubsByCondition(String keyword, RecruitmentStatus status, SortBy sortBy,
                                                          CentralClubCategory centralClubCategory, int page,
                                                          int size) {
        Page<Club> clubs = clubSearchRepository.findCentralClubsByCondition(
                keyword, status, sortBy, centralClubCategory, page, size);

        return getClubSearchResponseDTO(clubs);
    }

    @Override
    public ClubSearchResponse findUnionClubsByCondition(String keyword, RecruitmentStatus status, SortBy sortBy,
                                                        UnionClubCategory unionCategory, int page, int size) {
        Page<Club> clubs = clubSearchRepository.findUnionClubsByCondition(
                keyword, status, sortBy, unionCategory, page, size);

        return getClubSearchResponseDTO(clubs);
    }

    @Override
    public ClubSearchResponse findCollegeClubsByCondition(String keyword, RecruitmentStatus status, SortBy sortBy,
                                                          College college, int page, int size) {
        Page<Club> clubs = clubSearchRepository.findCollegeClubsByCondition(
                keyword, status, sortBy, college, page, size);

        return getClubSearchResponseDTO(clubs);
    }

    @Override
    public ClubSearchResponse findDepartmentClubsByCondition(String keyword, RecruitmentStatus status, SortBy sortBy,
                                                             College college, Department department, int page,
                                                             int size) {
        Page<Club> clubs = clubSearchRepository.findDepartmentClubsByCondition(
                keyword, status, sortBy, college, department, page, size);

        return getClubSearchResponseDTO(clubs);
    }

    @Override
    public ClubSearchResponse findPopularClubs(int page, int size) {
        Page<Club> clubs = clubSearchRepository.findPopularClubs(page, size);

        return getClubSearchResponseDTO(clubs);
    }

    @Override
    public ClubSearchResponse findThreeRecentUpdatedClubs() {
        Page<Club> clubs = clubSearchRepository.findRecentUpdateClubs(FIRST_PAGE, MAIN_PAGE_OFFSET);

        return getClubSearchResponseDTO(clubs);
    }

    @Override
    @Deprecated
    public ClubDetailListResponse findClubsByCondition(
            String name, CentralClubCategory category, RecruitmentStatus status, SortBy sortBy, int page,
            int size) {
        List<String> profileImageUrls = new ArrayList<>();
        if (sortBy != null && sortBy.equals(SortBy.RECRUITMENT_STATUS_ASC)) {
            Page<Club> clubs = clubRepository.findClubsOrderByRecruitmentStatus(name, category, status,
                    PageRequest.of(page, size));
            for (Club club : clubs) {
                profileImageUrls.add(s3Service.getDownloadUrl(club.getImageFile().getId()));
            }

            return ClubDetailListResponse.of(clubs, List.of());
        }

        Sort sort = (sortBy != null) ? sortBy.getSort() : SortBy.NAME_ASC.getSort();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Club> clubs = clubRepository.findAll(ClubSpecifications.findByCondition(name, category, status), pageable);
        for (Club club : clubs) {
            profileImageUrls.add(s3Service.getDownloadUrl(club.getImageFile().getId()));
        }
        return ClubDetailListResponse.of(clubs, profileImageUrls);
    }


    private void validateIsClubExistsById(Long clubId) {
        if (!clubRepository.existsById(clubId)) {
            throw new GeneralException(ErrorStatus._CLUB_NOT_FOUND);
        }
    }

    private Club getClub(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));
    }

    private IntroductionDraft getIntroduction(Long clubId) {
        return introductionDraftRepository.findByClubId(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._INTRODUCTION_DRAFT_NOT_FOUND));
    }

    private RecruitmentDraft getRecruitment(Long clubId) {
        return recruitmentDraftRepository.findByClubId(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._RECRUITMENT_DRAFT_NOT_FOUND));
    }

    private ClubDetailDraft getClubDetailDraft(Long clubId) {
        return clubDetailDraftRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_DETAIL_DRAFT_NOT_FOUND));
    }

    private ScheduleDescriptionDraft getScheduleDescriptionDraft(Long clubId) {
        return scheduleDescriptionDraftRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._SCHEDULE_DESCRIPTION_DRAFT_NOT_FOUND));
    }

    private Introduction getIntroductionOrElseNull(Long clubId) {
        return introductionRepository.findByClubId(clubId)
                .orElse(null);
    }

    private Recruitment getRecruitmentOrElseNull(Long clubId) {
        return recruitmentRepository.findByClubId(clubId)
                .orElse(null);
    }

    private String getInstagramProfileUrlOrElseNull(Long clubId) {
        ClubInstagramImage instaImage = clubInstagramImageRepository.findById(clubId)
                .orElse(null);

        if (instaImage != null) {
            return instaImage.getUrl();
        }
        return null;
    }

    private String resolveImageUrl(Club club) {
        if (club.getImageFile() == null) {
            return null;
        }
        return s3Service.getDownloadUrl(club.getImageFile().getId());
    }

    private String resolveImageUrl(ClubRegistration clubRegistration) {
        if (clubRegistration.getImageFile() == null) {
            return null;
        }
        return s3Service.getDownloadUrl(clubRegistration.getImageFile().getId());
    }



    private ClubSearchResponse getClubSearchResponseDTO(Page<Club> clubs) {
        Page<ClubSearchResult> dtoPage = clubs.map(club ->
                ClubSearchResult.of(
                        club.getId(),
                        club.getName(),
                        club.getOneLiner(),
                        resolveImageUrl(club),
                        club.getCategoryInfo().getClubType().getDescription(),
                        club.getRecruitmentStatus(),
                        CategoryResponse.getTag(club.getCategoryInfo())
                )
        );

        return ClubSearchResponse.of(dtoPage);
    }

    private ClubSearchResponse getClubSearchResponseDTOForUpdate(Page<ClubRegistration> clubRegistrations) {
        Page<ClubSearchResult> dtoPage = clubRegistrations.map(clubRegistration ->
            ClubSearchResult.of(
                clubRegistration.getId(),
                clubRegistration.getName(),
                clubRegistration.getOneLiner(),
                resolveImageUrl(clubRegistration),
                clubRegistration.getCategoryInfo().getClubType().getDescription(),
                null,
                CategoryResponse.getTag(clubRegistration.getCategoryInfo())
            )
        );

        return ClubSearchResponse.of(dtoPage);
    }

    @Override
    public Club getReference(Long clubId) {
        return clubRepository.getReferenceById(clubId);
    }

    @Override
    public ClubSearchResponse findUpdateRequests(int page, int size) {
        Page<ClubRegistration> all = clubRegistrationRepository.findAllByCommand(PageRequest.of(page, size), Command.UPDATE);
        return getClubSearchResponseDTOForUpdate(all);
    }

    @Override
    public GetInstagrams findInstagramsByCategory(ClubType type, int page, int size) {

        Page<Club> clubs = clubSearchRepository.findClubsByType(type, page, size);

        return getGetInstagramsDTO(clubs);
    }

    @Override
    public GetInstagrams findInstagramsCentral(CentralClubCategory category, int page, int size) {

        Page<Club> clubs = clubSearchRepository.findCentralClubsByCondition(
                null, null, null, category, page, size);

        return getGetInstagramsDTO(clubs);
    }

    @Override
    public GetInstagrams findInstagramsUnion(UnionClubCategory category, int page, int size) {

        Page<Club> clubs = clubSearchRepository.findUnionClubsByCondition(
                null, null, null, category, page, size
        );

        return getGetInstagramsDTO(clubs);
    }

    @Override
    public GetInstagrams findInstagramsCollege(College college, int page, int size) {

        Page<Club> clubs = clubSearchRepository.findCollegeClubsByCondition(
                null, null, null, college, page, size
        );

        return getGetInstagramsDTO(clubs);
    }

    @Override
    public GetInstagrams findInstagramsDepartment(Department department, int page, int size) {

        Page<Club> clubs = clubSearchRepository.findDepartmentClubsByCondition(
                null, null, null, null, department, page, size
        );

        return getGetInstagramsDTO(clubs);
    }

    @Override
    public GetInstagramsMain findInstagramsMain() {
        List<Club> clubs = clubSearchRepository.findClubByRandom(MAIN_INSTAGRAM_OFFSET);

        return getGetInstagramsMainDTO(clubs);
    }


    private GetInstagramsMain getGetInstagramsMainDTO(List<Club> clubs) {
        List<ClubInstagramDTO> dtoList = clubs.stream().map(this::getClubInstagramDTO).toList();

        return GetInstagramsMain.of(dtoList);
    }
    private GetInstagrams getGetInstagramsDTO(Page<Club> clubs) {

        Page<ClubInstagramDTO> dtoPage = clubs.map(this::getClubInstagramDTO);

        return GetInstagrams.from(dtoPage);
    }

    private ClubInstagramDTO getClubInstagramDTO(Club club) {
        String clubName = club.getName();
        String account = club.getSnsUrl();
        String profileImageUrl = resolveImageUrl(club);
        String profileUrl = INSTAGRAM_URL + account;
        return ClubInstagramDTO.of(clubName, account, profileImageUrl, profileUrl);
    }
}
