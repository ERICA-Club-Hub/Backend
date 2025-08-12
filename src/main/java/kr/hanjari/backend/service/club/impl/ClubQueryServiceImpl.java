package kr.hanjari.backend.service.club.impl;

import java.util.ArrayList;
import java.util.List;
import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.ClubRegistration;
import kr.hanjari.backend.domain.Introduction;
import kr.hanjari.backend.domain.Recruitment;
import kr.hanjari.backend.domain.Schedule;
import kr.hanjari.backend.domain.draft.ClubDetailDraft;
import kr.hanjari.backend.domain.draft.IntroductionDraft;
import kr.hanjari.backend.domain.draft.RecruitmentDraft;
import kr.hanjari.backend.domain.draft.ScheduleDraft;
import kr.hanjari.backend.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.enums.SortBy;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.repository.ClubRegistrationRepository;
import kr.hanjari.backend.repository.ClubRepository;
import kr.hanjari.backend.repository.IntroductionRepository;
import kr.hanjari.backend.repository.RecruitmentRepository;
import kr.hanjari.backend.repository.ScheduleRepository;
import kr.hanjari.backend.repository.draft.ClubDetailDraftRepository;
import kr.hanjari.backend.repository.draft.IntroductionDraftRepository;
import kr.hanjari.backend.repository.draft.RecruitmentDraftRepository;
import kr.hanjari.backend.repository.draft.ScheduleDraftRepository;
import kr.hanjari.backend.repository.specification.ClubSpecifications;
import kr.hanjari.backend.service.club.ClubQueryService;
import kr.hanjari.backend.service.s3.S3Service;
import kr.hanjari.backend.web.dto.club.response.ClubIntroductionResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubOverviewResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubRecruitmentResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubRegistrationDTO;
import kr.hanjari.backend.web.dto.club.response.ClubResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubScheduleResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubSearchResponseDTO;
import kr.hanjari.backend.web.dto.club.response.GetRegistrationsResponseDTO;
import kr.hanjari.backend.web.dto.club.response.draft.ClubBasicInfoResponseDTO;
import kr.hanjari.backend.web.dto.club.response.draft.ClubDetailDraftResponseDTO;
import kr.hanjari.backend.web.dto.club.response.draft.ClubIntroductionDraftResponseDTO;
import kr.hanjari.backend.web.dto.club.response.draft.ClubRecruitmentDraftResponseDTO;
import kr.hanjari.backend.web.dto.club.response.draft.ClubScheduleDraftResponseDTO;
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

    private final S3Service s3Service;


    @Override
    public GetRegistrationsResponseDTO getRegistrations() {
        List<ClubRegistration> clubRegistrationList = clubRegistrationRepository.findAll();
        List<ClubRegistrationDTO> clubRegistrationDTOList = clubRegistrationList.stream()
                .map(ClubRegistrationDTO::from)
                .toList();

        return GetRegistrationsResponseDTO.of(clubRegistrationDTOList);
    }

    @Override
    public ClubSearchResponseDTO findClubsByCondition(
            String name, CentralClubCategory category, RecruitmentStatus status, SortBy sortBy, int page,
            int size) {
        List<String> profileImageUrls = new ArrayList<>();
        if (sortBy != null && sortBy.equals(SortBy.RECRUITMENT_STATUS_ASC)) {
            Page<Club> clubs = clubRepository.findClubsOrderByRecruitmentStatus(name, category, status,
                    PageRequest.of(page, size));
            for (Club club : clubs) {
                profileImageUrls.add(s3Service.getDownloadUrl(club.getImageFile().getId()));
            }

            return ClubSearchResponseDTO.of(clubs, List.of());
        }

        Sort sort = (sortBy != null) ? sortBy.getSort() : SortBy.NAME_ASC.getSort();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Club> clubs = clubRepository.findAll(ClubSpecifications.findByCondition(name, category, status), pageable);
        for (Club club : clubs) {
            profileImageUrls.add(s3Service.getDownloadUrl(club.getImageFile().getId()));
        }
        return ClubSearchResponseDTO.of(clubs, profileImageUrls);
    }

    @Override
    public ClubResponseDTO findClubDetail(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

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

}
