package kr.hanjari.backend.domain.club.application.query.impl;

import java.util.ArrayList;
import java.util.List;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.entity.ClubRegistration;
import kr.hanjari.backend.domain.club.domain.entity.detail.Introduction;
import kr.hanjari.backend.domain.club.domain.entity.detail.Recruitment;
import kr.hanjari.backend.domain.club.domain.entity.detail.Schedule;
import kr.hanjari.backend.domain.club.domain.entity.draft.ClubDetailDraft;
import kr.hanjari.backend.domain.club.domain.entity.draft.IntroductionDraft;
import kr.hanjari.backend.domain.club.domain.entity.draft.RecruitmentDraft;
import kr.hanjari.backend.domain.club.domain.entity.draft.ScheduleDraft;
import kr.hanjari.backend.domain.club.enums.CentralClubCategory;
import kr.hanjari.backend.domain.club.enums.College;
import kr.hanjari.backend.domain.club.enums.Department;
import kr.hanjari.backend.domain.club.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.enums.SortBy;
import kr.hanjari.backend.domain.club.enums.UnionClubCategory;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;
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
import kr.hanjari.backend.domain.club.application.command.ClubCommandService;
import kr.hanjari.backend.domain.club.application.query.ClubQueryService;
import kr.hanjari.backend.infrastructure.s3.S3Service;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubDetailListResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubIntroductionResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubOverviewResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubRecruitmentResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubRegistrationDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubScheduleResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubSearchResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubSearchResponseDTO.ClubSearchResult;
import kr.hanjari.backend.domain.club.presentation.dto.response.GetRegistrationsResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubBasicInfoResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubDetailDraftResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubIntroductionDraftResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubRecruitmentDraftResponseDTO;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubScheduleDraftResponseDTO;
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
    public GetRegistrationsResponseDTO getRegistrations() {
        List<ClubRegistration> clubRegistrationList = clubRegistrationRepository.findAll();
        List<ClubRegistrationDTO> clubRegistrationDTOList = clubRegistrationList.stream()
                .map(ClubRegistrationDTO::from)
                .toList();

        return GetRegistrationsResponseDTO.of(clubRegistrationDTOList);
    }

    @Override
    public ClubDetailListResponseDTO findClubsByCondition(
            String name, CentralClubCategory category, RecruitmentStatus status, SortBy sortBy, int page,
            int size) {
        List<String> profileImageUrls = new ArrayList<>();
        if (sortBy != null && sortBy.equals(SortBy.RECRUITMENT_STATUS_ASC)) {
            Page<Club> clubs = clubRepository.findClubsOrderByRecruitmentStatus(name, category, status,
                    PageRequest.of(page, size));
            for (Club club : clubs) {
                profileImageUrls.add(s3Service.getDownloadUrl(club.getImageFile().getId()));
            }

            return ClubDetailListResponseDTO.of(clubs, List.of());
        }

        Sort sort = (sortBy != null) ? sortBy.getSort() : SortBy.NAME_ASC.getSort();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Club> clubs = clubRepository.findAll(ClubSpecifications.findByCondition(name, category, status), pageable);
        for (Club club : clubs) {
            profileImageUrls.add(s3Service.getDownloadUrl(club.getImageFile().getId()));
        }
        return ClubDetailListResponseDTO.of(clubs, profileImageUrls);
    }

    @Override
    public ClubResponseDTO findClubDetail(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));
        clubCommandService.incrementClubViewCount(clubId);

        return ClubResponseDTO.of(club, s3Service.getDownloadUrl(club.getImageFile().getId()));
    }

    @Override
    public ClubOverviewResponseDTO findClubOverview(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));
        return ClubOverviewResponseDTO.of(club, s3Service.getDownloadUrl(club.getImageFile().getId()));
    }

    @Override
    public ClubBasicInfoResponseDTO findClubBasicInfo(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));
        return ClubBasicInfoResponseDTO.of(club);
    }

    @Override
    public ClubDetailDraftResponseDTO findClubDetailDraft(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        ClubDetailDraft clubDetailDraft = clubDetailDraftRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_DETAIL_DRAFT_NOT_FOUND));

        return ClubDetailDraftResponseDTO.of(clubDetailDraft, club,
                s3Service.getDownloadUrl(club.getImageFile().getId()));
    }

    @Override
    public ClubScheduleResponseDTO findAllClubActivities(Long clubId) {
        if (!clubRepository.existsById(clubId)) {
            throw new GeneralException(ErrorStatus._CLUB_NOT_FOUND);
        }

        List<Schedule> schedules = scheduleRepository.findAllByClubId(clubId);

        return ClubScheduleResponseDTO.of(schedules);
    }

    @Override
    public ClubScheduleDraftResponseDTO findAllClubActivitiesDraft(Long clubId) {
        if (!clubRepository.existsById(clubId)) {
            throw new GeneralException(ErrorStatus._CLUB_NOT_FOUND);
        }
        List<ScheduleDraft> schedules = scheduleDraftRepository.findAllByClubIdOrderByMonth(clubId);

        return ClubScheduleDraftResponseDTO.of(schedules);
    }


    @Override
    public ClubIntroductionResponseDTO findClubIntroduction(Long clubId) {
        if (!clubRepository.existsById(clubId)) {
            throw new GeneralException(ErrorStatus._CLUB_NOT_FOUND);
        }
        Introduction introduction = introductionRepository.findByClubId(clubId)
                .orElse(null);

        return ClubIntroductionResponseDTO.of(introduction);
    }

    @Override
    public ClubIntroductionDraftResponseDTO findClubIntroductionDraft(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        IntroductionDraft introduction = introductionDraftRepository.findByClubId(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._INTRODUCTION_DRAFT_NOT_FOUND));

        return ClubIntroductionDraftResponseDTO.of(club, introduction,
                s3Service.getDownloadUrl(club.getImageFile().getId()));
    }

    @Override
    public ClubRecruitmentResponseDTO findClubRecruitment(Long clubId) {
        Recruitment recruitment = recruitmentRepository.findByClubId(clubId)
                .orElse(null);
        Introduction introduction = introductionRepository.findByClubId(clubId)
                .orElse(null);

        String target = (introduction != null) ? introduction.getContent2() : null;
        return ClubRecruitmentResponseDTO.of(recruitment, target);
    }

    @Override
    public ClubRecruitmentDraftResponseDTO findClubRecruitmentDraft(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        RecruitmentDraft recruitment = recruitmentDraftRepository.findByClubId(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._RECRUITMENT_DRAFT_NOT_FOUND));

        return ClubRecruitmentDraftResponseDTO.of(club, recruitment,
                s3Service.getDownloadUrl(club.getImageFile().getId()));
    }

    @Override
    public ClubSearchResponseDTO findCentralClubsByCondition(String keyword, RecruitmentStatus status, SortBy sortBy,
                                                             CentralClubCategory centralClubCategory, int page,
                                                             int size) {
        Page<Club> clubs = clubSearchRepository.findCentralClubsByCondition(
                keyword, status, sortBy, centralClubCategory, page, size);

        return getClubSearchResponseDTO(clubs);
    }

    @Override
    public ClubSearchResponseDTO findUnionClubsByCondition(String keyword, RecruitmentStatus status, SortBy sortBy,
                                                           UnionClubCategory unionCategory, int page, int size) {
        Page<Club> clubs = clubSearchRepository.findUnionClubsByCondition(
                keyword, status, sortBy, unionCategory, page, size);

        return getClubSearchResponseDTO(clubs);
    }

    @Override
    public ClubSearchResponseDTO findCollegeClubsByCondition(String keyword, RecruitmentStatus status, SortBy sortBy,
                                                             College college, int page, int size) {
        Page<Club> clubs = clubSearchRepository.findCollegeClubsByCondition(
                keyword, status, sortBy, college, page, size);

        return getClubSearchResponseDTO(clubs);
    }

    @Override
    public ClubSearchResponseDTO findDepartmentClubsByCondition(String keyword, RecruitmentStatus status, SortBy sortBy,
                                                                College college, Department department, int page,
                                                                int size) {
        Page<Club> clubs = clubSearchRepository.findDepartmentClubsByCondition(
                keyword, status, sortBy, college, department, page, size);

        return getClubSearchResponseDTO(clubs);
    }

    @Override
    public ClubSearchResponseDTO findPopularClubs(int page, int size) {
        Page<Club> clubs = clubSearchRepository.findPopularClubs(page, size);

        return getClubSearchResponseDTO(clubs);
    }

    private String resolveImageUrl(Club club) {
        if (club.getImageFile() == null) {
            return null;
        }
        return s3Service.getDownloadUrl(club.getImageFile().getId());
    }

    private ClubSearchResponseDTO getClubSearchResponseDTO(Page<Club> clubs) {
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

        return ClubSearchResponseDTO.of(dtoPage);
    }
}
