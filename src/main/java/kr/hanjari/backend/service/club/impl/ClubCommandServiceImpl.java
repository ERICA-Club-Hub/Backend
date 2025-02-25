package kr.hanjari.backend.service.club.impl;


import kr.hanjari.backend.domain.*;
import kr.hanjari.backend.domain.draft.IntroductionDraft;
import kr.hanjari.backend.domain.draft.RecruitmentDraft;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.repository.*;
import kr.hanjari.backend.repository.draft.IntroductionDraftRepository;
import kr.hanjari.backend.repository.draft.RecruitmentDraftRepository;
import kr.hanjari.backend.security.token.JwtTokenProvider;
import kr.hanjari.backend.service.club.ClubCommandService;
import kr.hanjari.backend.service.s3.S3Service;
import kr.hanjari.backend.web.dto.club.request.*;
import kr.hanjari.backend.web.dto.club.response.ScheduleResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClubCommandServiceImpl implements ClubCommandService {

    private final ClubRepository clubRepository;
    private final ClubRegistrationRepository clubRegistrationRepository;
    private final IntroductionRepository introductionRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ScheduleRepository scheduleRepository;

    private final IntroductionDraftRepository introductionDraftRepository;
    private final RecruitmentDraftRepository recruitmentDraftRepository;

    private final S3Service s3Service;

    @Override
    public Long requestClubRegistration(CommonClubDTO requestBody, MultipartFile image) {

        File imageFile = s3Service.uploadFile(image);

        ClubRegistration clubRegistration = requestBody.toClubRegistration();
        clubRegistration.updateImageFile(imageFile);

        clubRegistrationRepository.save(clubRegistration);

        return clubRegistration.getId();
    }

    @Override
    public Long acceptClubRegistration(Long clubRegistrationId) {

        ClubRegistration clubRegistration = clubRegistrationRepository.findById(clubRegistrationId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._BAD_REQUEST));

        Club newClub = clubRegistration.toClub();
        newClub.updateRecruitmentStatus(0);
        clubRepository.save(newClub);

        return newClub.getId();
    }

    @Override
    public Long saveClubDetail(Long clubId, ClubDetailRequestDTO clubDetailDTO) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));
        club.updateClubDetails(clubDetailDTO);
        Club saved = clubRepository.save(club);
        return saved.getId();
    }

    @Override
    public Long updateClubDetail(Long clubId, CommonClubDTO request, MultipartFile file) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));
        club.updateClubCommonInfo(request);

        File imageFile = s3Service.uploadFile(file);
        club.updateClubImage(imageFile);

        Club saved = clubRepository.save(club);

        return saved.getId();
    }

    @Override
    public ScheduleResponseDTO saveClubSchedule(Long clubId, ClubScheduleRequestDTO clubScheduleDTO) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        Schedule schedule = Schedule.builder()
                .club(club)
                .month(clubScheduleDTO.month())
                .content(clubScheduleDTO.content())
                .build();

        Schedule save = scheduleRepository.save(schedule);
        return ScheduleResponseDTO.of(save);
    }

    @Override
    public ScheduleResponseDTO updateClubSchedule(Long clubId, Long scheduleId, ClubScheduleRequestDTO clubScheduleDTO) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._SCHEDULE_NOT_FOUND));

        // 동아리에 속한 스케줄인지 확인
        if (!club.getId().equals(schedule.getClub().getId())) {
            throw new GeneralException(ErrorStatus._SCHEDULE_IS_NOT_BELONG_TO_CLUB);
        }

        schedule.updateSchedule(clubScheduleDTO.month(), clubScheduleDTO.content());

        Schedule save = scheduleRepository.save(schedule);

        return ScheduleResponseDTO.of(save);
    }

    @Override
    public void deleteClubSchedule(Long clubId, Long scheduleId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new GeneralException(ErrorStatus._SCHEDULE_NOT_FOUND));

        if (!club.getId().equals(schedule.getClub().getId())) {
            throw new GeneralException(ErrorStatus._SCHEDULE_IS_NOT_BELONG_TO_CLUB);
        }

        scheduleRepository.deleteById(scheduleId); // 기존 스케줄 삭제
    }

    @Override
    public Long saveClubIntroduction(Long clubId, ClubIntroductionRequestDTO clubIntroductionDTO) {

        if (!clubRepository.existsById(clubId)) {
            throw new GeneralException(ErrorStatus._CLUB_NOT_FOUND);
        }

        // 기존 Introduction 조회
        Introduction introduction = introductionRepository.findById(clubId)
                .orElseGet(() -> Introduction.builder().clubId(clubId).build());

        // Introduction 내용 업데이트
        introduction.updateIntroduction(clubIntroductionDTO.introduction(),
                clubIntroductionDTO.activity(), clubIntroductionDTO.recruitment());

        // 저장
        Introduction saved = introductionRepository.save(introduction);

        if (introductionDraftRepository.existsByClubId(clubId)) {
            introductionDraftRepository.removeByClubId(clubId);
        }

        return saved.getClubId();
    }

    @Override
    public Long saveClubIntroductionDraft(Long clubId, ClubIntroductionRequestDTO clubIntroductionDTO) {
        if (!clubRepository.existsById(clubId)) {
            throw new GeneralException(ErrorStatus._CLUB_NOT_FOUND);
        }

        // 기존 IntroductionDraft 조회
        IntroductionDraft introductionDraft = introductionDraftRepository.findById(clubId)
                .orElseGet(() -> IntroductionDraft.builder().clubId(clubId).build());

        // IntroductionDraft 내용 업데이트
        introductionDraft.updateIntroduction(clubIntroductionDTO.introduction(),
                clubIntroductionDTO.activity(), clubIntroductionDTO.recruitment());

        // 저장
        IntroductionDraft saved = introductionDraftRepository.save(introductionDraft);

        return saved.getClubId();
    }



    @Override
    public Long saveClubRecruitment(Long clubId, ClubRecruitmentRequestDTO clubRecruitmentDTO) {
        if (!clubRepository.existsById(clubId))
            throw new GeneralException(ErrorStatus._CLUB_NOT_FOUND);

        Recruitment recruitment = recruitmentRepository.findById(clubId)
                .orElseGet(() -> Recruitment.builder().clubId(clubId).build());

        recruitment.updateRecruitment(clubRecruitmentDTO);
        Recruitment save = recruitmentRepository.save(recruitment);

        if (recruitmentDraftRepository.existsByClubId(clubId))
            recruitmentDraftRepository.removeByClubId(clubId);

        return save.getClubId();
    }

    @Override
    public Long saveClubRecruitmentDraft(Long clubId, ClubRecruitmentRequestDTO clubRecruitmentDTO) {
        if (!clubRepository.existsById(clubId))
            throw new GeneralException(ErrorStatus._CLUB_NOT_FOUND);


        RecruitmentDraft recruitment = recruitmentDraftRepository.findById(clubId)
                .orElseGet(() -> RecruitmentDraft.builder().clubId(clubId).build());

        recruitment.updateRecruitment(clubRecruitmentDTO);
        RecruitmentDraft save = recruitmentDraftRepository.save(recruitment);

        return save.getClubId();
    }

}
