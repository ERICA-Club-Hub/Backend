package kr.hanjari.backend.domain.club.application.query.impl;

import java.util.ArrayList;
import java.util.List;
import kr.hanjari.backend.domain.club.application.command.ClubCommandService;
import kr.hanjari.backend.domain.club.application.query.ClubQueryService;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.ClubRegistration;
import kr.hanjari.backend.domain.club.domain.entity.detail.Introduction;
import kr.hanjari.backend.domain.club.domain.entity.detail.Recruitment;
import kr.hanjari.backend.domain.club.domain.entity.detail.Schedule;
import kr.hanjari.backend.domain.club.domain.entity.draft.ClubDetailDraft;
import kr.hanjari.backend.domain.club.domain.entity.draft.IntroductionDraft;
import kr.hanjari.backend.domain.club.domain.entity.draft.RecruitmentDraft;
import kr.hanjari.backend.domain.club.domain.entity.draft.ScheduleDraft;
import kr.hanjari.backend.domain.club.domain.repository.ClubRegistrationRepository;
import kr.hanjari.backend.domain.club.domain.repository.ClubRepository;
import kr.hanjari.backend.domain.club.domain.repository.detail.IntroductionRepository;
import kr.hanjari.backend.domain.club.domain.repository.detail.RecruitmentRepository;
import kr.hanjari.backend.domain.club.domain.repository.detail.ScheduleRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.ClubDetailDraftRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.IntroductionDraftRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.RecruitmentDraftRepository;
import kr.hanjari.backend.domain.club.domain.repository.draft.ScheduleDraftRepository;
import kr.hanjari.backend.domain.club.domain.repository.search.ClubSearchRepository;
import kr.hanjari.backend.domain.club.domain.repository.search.ClubSpecifications;
import kr.hanjari.backend.domain.club.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.club.domain.enums.College;
import kr.hanjari.backend.domain.club.domain.enums.Department;
import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.domain.enums.SortBy;
import kr.hanjari.backend.domain.club.domain.enums.UnionClubCategory;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubDetailListResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubIntroductionResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubOverviewResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubRecruitmentResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubRegistrationResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubScheduleResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubSearchResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubSearchResponse.ClubSearchResult;
import kr.hanjari.backend.domain.club.presentation.dto.response.GetRegistrationsResponse;
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

    private final ClubRepository clubRepository;
    private final ClubRegistrationRepository clubRegistrationRepository;
    private final IntroductionRepository introductionRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ScheduleRepository scheduleRepository;

    private final IntroductionDraftRepository introductionDraftRepository;
    private final RecruitmentDraftRepository recruitmentDraftRepository;
    private final ClubDetailDraftRepository clubDetailDraftRepository;
    private final ScheduleDraftRepository scheduleDraftRepository;

    private final ClubSearchRepository clubSearchRepository;

    private final S3Service s3Service;
    private final ClubCommandService clubCommandService;


    @Override
    public GetRegistrationsResponse getRegistrations() {
        List<ClubRegistration> clubRegistrationList = clubRegistrationRepository.findAll();

        List<ClubRegistrationResponse> clubRegistrationResponseDTOList = clubRegistrationList.stream()
                .map(ClubRegistrationResponse::from)
                .toList();

        return GetRegistrationsResponse.of(clubRegistrationResponseDTOList);
    }

    @Override
    public ClubResponse findClubDetail(Long clubId) {
        Club club = getClub(clubId);
        clubCommandService.incrementClubViewCount(clubId);

        return ClubResponse.of(club, s3Service.getDownloadUrl(club.getImageFile().getId()));
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

        return ClubDetailDraftResponse.of(clubDetailDraft, club,
                s3Service.getDownloadUrl(club.getImageFile().getId()));
    }

    @Override
    public ClubScheduleResponse findAllClubActivities(Long clubId) {
        validateIsClubExistsById(clubId);
        List<Schedule> schedules = scheduleRepository.findAllByClubId(clubId);

        return ClubScheduleResponse.of(schedules);
    }

    @Override
    public ClubScheduleDraftResponse findAllClubActivitiesDraft(Long clubId) {
        validateIsClubExistsById(clubId);
        List<ScheduleDraft> schedules = scheduleDraftRepository.findAllByClubIdOrderByMonth(clubId);

        return ClubScheduleDraftResponse.of(schedules);
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
        Introduction introduction = getIntroductionOrElseNull(clubId);

        String target = (introduction != null) ? introduction.getContent2() : null;
        return ClubRecruitmentResponse.of(recruitment, target);
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

    private Introduction getIntroductionOrElseNull(Long clubId) {
        return introductionRepository.findByClubId(clubId)
                .orElse(null);
    }

    private Recruitment getRecruitmentOrElseNull(Long clubId) {
        return recruitmentRepository.findByClubId(clubId)
                .orElse(null);
    }

    private String resolveImageUrl(Club club) {
        if (club.getImageFile() == null) {
            return null;
        }
        return s3Service.getDownloadUrl(club.getImageFile().getId());
    }

    private ClubSearchResponse getClubSearchResponseDTO(Page<Club> clubs) {
        Page<ClubSearchResult> dtoPage = clubs.map(club ->
                ClubSearchResult.of(
                        club.getId(),
                        club.getName(),
                        club.getOneLiner(),
                        resolveImageUrl(club),
                        club.getCategoryInfo().getClubType().getDescription(),
                        club.getRecruitmentStatus()
                )
        );

        return ClubSearchResponse.of(dtoPage);
    }

    @Override
    public Club getReference(Long clubId) {
        return clubRepository.getReferenceById(clubId);
    }
}
