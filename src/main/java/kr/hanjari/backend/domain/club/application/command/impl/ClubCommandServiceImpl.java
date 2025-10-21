package kr.hanjari.backend.domain.club.application.command.impl;


import java.util.ArrayList;
import java.util.List;
import kr.hanjari.backend.domain.club.application.ClubUtil;
import kr.hanjari.backend.domain.club.application.command.ClubCommandService;
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
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubBasicInformationRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubDetailRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubIntroductionRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubRecruitmentRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubScheduleListRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubScheduleRequest;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubCommandResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.ScheduleListResponse;
import kr.hanjari.backend.domain.club.presentation.dto.response.draft.ClubScheduleDraftResponse;
import kr.hanjari.backend.domain.file.application.FileService;
import kr.hanjari.backend.domain.file.domain.entity.File;
import kr.hanjari.backend.domain.file.domain.repository.FileRepository;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClubCommandServiceImpl implements ClubCommandService {

    @Value("${login.url}")
    private String loginURL;

    private final FileRepository fileRepository;
    private final ClubRepository clubRepository;
    private final ClubRegistrationRepository clubRegistrationRepository;
    private final IntroductionRepository introductionRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ScheduleRepository scheduleRepository;

    private final IntroductionDraftRepository introductionDraftRepository;
    private final RecruitmentDraftRepository recruitmentDraftRepository;
    private final ClubDetailDraftRepository clubDetailDraftRepository;
    private final ScheduleDraftRepository scheduleDraftRepository;

    private final ClubUtil clubUtil;
    private final FileService fileService;

    @Override
    public ClubCommandResponse requestClubRegistration(ClubBasicInformationRequest requestBody, MultipartFile file) {

        ClubRegistration clubRegistration = ClubRegistration.create(requestBody.clubName(), requestBody.leaderEmail(),
                requestBody.toCategoryCommand(), requestBody.oneLiner(), requestBody.briefIntroduction());

        Long fileId = fileService.uploadObjectAndSaveFile(file);
        File imageFile = fileRepository.getReferenceById(fileId);
        clubRegistration.updateImageFile(imageFile);

        clubRegistrationRepository.save(clubRegistration);

        return ClubCommandResponse.of(clubRegistration.getId());
    }

    @Override
    public ClubCommandResponse acceptClubRegistration(Long clubRegistrationId) {

        ClubRegistration clubRegistration = getClubRegistration(clubRegistrationId);

        Club newClub = clubRepository.save(Club.create(clubRegistration));
        clubRegistrationRepository.deleteById(clubRegistrationId);

        Long newClubId = newClub.getId();
        String code = clubUtil.reissueClubCode(newClubId);
        clubUtil.sendEmail(newClub.getLeaderEmail(), newClub.getName(), code, loginURL);

        return ClubCommandResponse.of(newClub.getId());
    }

    @Override
    public void deleteClubRegistration(Long clubRegistrationId) {

        ClubRegistration clubRegistrationToDelete = getClubRegistration(clubRegistrationId);

        fileService.deleteObjectAndFile(clubRegistrationToDelete.getImageFile().getId());
        clubRegistrationRepository.deleteById(clubRegistrationId);

    }

    @Override
    public ClubCommandResponse saveClubDetail(Long clubId, ClubDetailRequest clubDetailDTO) {
        Club club = getClub(clubId);
        club.updateClubDetails(clubDetailDTO);
        Club saved = clubRepository.save(club);

        if (clubDetailDraftRepository.existsById(clubId)) {
            clubDetailDraftRepository.deleteById(clubId);
        }
        return ClubCommandResponse.of(saved.getId());
    }

    @Override
    public ClubCommandResponse updateClubBasicInformation(Long clubId, ClubBasicInformationRequest request,
                                                          MultipartFile file) {
        Club club = getClub(clubId);
        club.updateClubCommonInfo(request, request.toCategoryCommand());

        if (file != null) {
            Long fileId = fileService.uploadObjectAndSaveFile(file);
            fileService.deleteObjectAndFile(club.getImageFile().getId());
            File imageFile = fileRepository.getReferenceById(fileId);
            club.updateClubImage(imageFile);
        }

        Club saved = clubRepository.save(club);
        return ClubCommandResponse.of(saved.getId());
    }

    @Override
    public ClubCommandResponse saveClubDetailDraft(Long clubId, ClubDetailRequest clubDetailDTO) {
        validateIsExistClub(clubId);
        ClubDetailDraft clubDetailDraft = getClubDetailDraftOrCreate(clubId);
        clubDetailDraft.updateClubDetails(clubDetailDTO);
        ClubDetailDraft save = clubDetailDraftRepository.save(clubDetailDraft);
        return ClubCommandResponse.of(save.getClubId());
    }


    @Override
    public ScheduleListResponse saveAndUpdateClubSchedule(Long clubId, ClubScheduleListRequest clubScheduleDTO) {
        Club club = getClub(clubId);

        List<Schedule> schedules = new ArrayList<>();

        for (ClubScheduleRequest request : clubScheduleDTO.schedules()) {
            if (request.scheduleId() == null) {
                Schedule schedule = Schedule.builder()
                        .club(club)
                        .month(request.month())
                        .content(request.content())
                        .build();
                schedules.add(scheduleRepository.save(schedule));
                scheduleDraftRepository.deleteByClubId(clubId);
            } else {
                Schedule schedule = getSchedule(request.scheduleId());

                if (!club.getId().equals(schedule.getClub().getId())) {
                    throw new GeneralException(ErrorStatus._SCHEDULE_IS_NOT_BELONG_TO_CLUB);
                }

                schedule.updateSchedule(request.month(), request.content());
                schedules.add(scheduleRepository.save(schedule));
                scheduleDraftRepository.deleteByClubId(clubId);
            }
        }
        return ScheduleListResponse.of(schedules);
    }

    @Override
    public ClubScheduleDraftResponse saveAndUpdateClubScheduleDraft(Long clubId,
                                                                    ClubScheduleListRequest clubActivityDTO) {

        Club club = getClub(clubId);

        List<ScheduleDraft> scheduleDrafts = new ArrayList<>();

        for (ClubScheduleRequest request : clubActivityDTO.schedules()) {
            if (request.scheduleId() == null) {
                ScheduleDraft scheduleDraft = ScheduleDraft.builder()
                        .club(club)
                        .month(request.month())
                        .content(request.content())
                        .build();
                scheduleDrafts.add(scheduleDraftRepository.save(scheduleDraft));
            } else {
                ScheduleDraft scheduleDraft = scheduleDraftRepository.findById(request.scheduleId())
                        .orElseThrow(() -> new GeneralException(ErrorStatus._SCHEDULE_NOT_FOUND));

                if (!club.getId().equals(scheduleDraft.getClub().getId())) {
                    throw new GeneralException(ErrorStatus._SCHEDULE_IS_NOT_BELONG_TO_CLUB);
                }

                scheduleDraft.updateSchedule(request.month(), request.content());
                scheduleDrafts.add(scheduleDraftRepository.save(scheduleDraft));
            }
        }
        return ClubScheduleDraftResponse.of(scheduleDrafts);
    }

//    @Override
//    public ScheduleResponseDTO updateClubSchedule(Long clubId, Long scheduleId, ClubScheduleRequestDTO clubScheduleDTO) {
//        Club club = clubRepository.findById(clubId)
//                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));
//
//        Schedule schedule = scheduleRepository.findById(scheduleId)
//                .orElseThrow(() -> new GeneralException(ErrorStatus._SCHEDULE_NOT_FOUND));
//
//        // 동아리에 속한 스케줄인지 확인
//        if (!club.getId().equals(schedule.getClub().getId())) {
//            throw new GeneralException(ErrorStatus._SCHEDULE_IS_NOT_BELONG_TO_CLUB);
//        }
//
//        schedule.updateSchedule(clubScheduleDTO.month(), clubScheduleDTO.content());
//
//        Schedule save = scheduleRepository.save(schedule);
//
//        return ScheduleResponseDTO.of(save);
//    }

    @Override
    public void deleteClubSchedule(Long clubId, Long scheduleId) {
        Club club = getClub(clubId);
        Schedule schedule = getSchedule(scheduleId);
        club.validateIsContainsInClub(schedule);
        scheduleRepository.deleteById(scheduleId);
    }

    @Override
    public ClubCommandResponse saveClubIntroduction(Long clubId, ClubIntroductionRequest clubIntroductionDTO) {

        validateIsExistClub(clubId);

        // 기존 Introduction 조회
        Introduction introduction = getIntroductionOrCreate(clubId);

        // Introduction 내용 업데이트
        introduction.updateIntroduction(clubIntroductionDTO.introduction(),
                clubIntroductionDTO.activity(), clubIntroductionDTO.recruitment());

        // 저장
        Introduction saved = introductionRepository.save(introduction);

        if (introductionDraftRepository.existsByClubId(clubId)) {
            introductionDraftRepository.removeByClubId(clubId);
        }

        return ClubCommandResponse.of(saved.getClubId());
    }

    @Override
    public ClubCommandResponse saveClubIntroductionDraft(Long clubId, ClubIntroductionRequest clubIntroductionDTO) {
        validateIsExistClub(clubId);

        // 기존 IntroductionDraft 조회
        IntroductionDraft introductionDraft = getIntroductionDraftOrCreate(clubId);

        // IntroductionDraft 내용 업데이트
        introductionDraft.updateIntroduction(clubIntroductionDTO.introduction(),
                clubIntroductionDTO.activity(), clubIntroductionDTO.recruitment());

        // 저장
        IntroductionDraft saved = introductionDraftRepository.save(introductionDraft);

        return ClubCommandResponse.of(saved.getClubId());
    }


    @Override
    public ClubCommandResponse saveClubRecruitment(Long clubId, ClubRecruitmentRequest clubRecruitmentDTO) {
        validateIsExistClub(clubId);

        Recruitment recruitment = getRecruitmentOrCreate(clubId);

        recruitment.updateRecruitment(clubRecruitmentDTO);
        Recruitment save = recruitmentRepository.save(recruitment);

        if (recruitmentDraftRepository.existsByClubId(clubId)) {
            recruitmentDraftRepository.removeByClubId(clubId);
        }

        return ClubCommandResponse.of(save.getClubId());
    }

    @Override
    public ClubCommandResponse saveClubRecruitmentDraft(Long clubId, ClubRecruitmentRequest clubRecruitmentDTO) {
        validateIsExistClub(clubId);

        RecruitmentDraft recruitment = getRecruitmentDraftOrCreate(clubId);

        recruitment.updateRecruitment(clubRecruitmentDTO);
        RecruitmentDraft save = recruitmentDraftRepository.save(recruitment);

        return ClubCommandResponse.of(save.getClubId());
    }

    @Override
    public void incrementClubViewCount(Long clubId) {
        Club club = getClub(clubId);
        club.incrementViewCount();
        clubRepository.save(club);
    }

    private Club getClub(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));
    }

    private ClubRegistration getClubRegistration(Long clubRegistrationId) {
        return clubRegistrationRepository.findById(clubRegistrationId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_REGISTRATION_NOT_FOUND));
    }

    private Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._SCHEDULE_NOT_FOUND));
    }

    private void validateIsExistClub(Long clubId) {
        if (!clubRepository.existsById(clubId)) {
            throw new GeneralException(ErrorStatus._CLUB_NOT_FOUND);
        }
    }

    private Introduction getIntroductionOrCreate(Long clubId) {
        return introductionRepository.findById(clubId)
                .orElseGet(() -> Introduction.builder().clubId(clubId).build());
    }

    private IntroductionDraft getIntroductionDraftOrCreate(Long clubId) {
        return introductionDraftRepository.findById(clubId)
                .orElseGet(() -> IntroductionDraft.builder().clubId(clubId).build());
    }

    private Recruitment getRecruitmentOrCreate(Long clubId) {
        return recruitmentRepository.findById(clubId)
                .orElseGet(() -> Recruitment.builder().clubId(clubId).build());
    }

    private RecruitmentDraft getRecruitmentDraftOrCreate(Long clubId) {
        return recruitmentDraftRepository.findById(clubId)
                .orElseGet(() -> RecruitmentDraft.builder().clubId(clubId).build());
    }

    private ClubDetailDraft getClubDetailDraftOrCreate(Long clubId) {
        return clubDetailDraftRepository.findById(clubId)
                .orElseGet(() -> ClubDetailDraft.builder().clubId(clubId).build());
    }
}
