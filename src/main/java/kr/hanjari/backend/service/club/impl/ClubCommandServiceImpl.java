package kr.hanjari.backend.service.club.impl;


import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.Introduction;
import kr.hanjari.backend.domain.Recruitment;
import kr.hanjari.backend.domain.Schedule;
import kr.hanjari.backend.domain.key.ScheduleId;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.repository.ClubRepository;
import kr.hanjari.backend.repository.IntroductionRepository;
import kr.hanjari.backend.repository.RecruitmentRepository;
import kr.hanjari.backend.repository.ScheduleRepository;
import kr.hanjari.backend.service.club.ClubCommandService;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubDetailDTO;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubIntroductionDTO;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubRecruitmentDTO;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubScheduleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClubCommandServiceImpl implements ClubCommandService {

    private final ClubRepository clubRepository;
    private final IntroductionRepository introductionRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public Long saveClubDetail(Long clubId, ClubDetailDTO clubDetailDTO) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));
        club.updateClubDetails(clubDetailDTO);
        Club saved = clubRepository.save(club);
        return saved.getId();
    }

    @Override
    public ScheduleId saveClubSchedule(Long clubId, ClubScheduleDTO clubScheduleDTO) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        ScheduleId scheduleId = ScheduleId.builder()
                .clubId(clubId)
                .month(clubScheduleDTO.getMonthToChange())
                .build();

        if (scheduleRepository.existsById(scheduleId)) {
            throw new GeneralException(ErrorStatus._SCHEDULE_ALREADY_EXISTS);
        }

        Schedule schedule = Schedule.builder()
                .club(club)
                .id(scheduleId)
                .content(clubScheduleDTO.getContent())
                .build();

        Schedule save = scheduleRepository.save(schedule);
        return save.getId();
    }

    @Override
    public ScheduleId updateClubSchedule(Long clubId, Integer month, ClubScheduleDTO clubScheduleDTO) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        // 기존 스케줄 조회
        ScheduleId scheduleIdObj = ScheduleId.builder()
                .clubId(clubId)
                .month(month)
                .build();

        Schedule schedule = scheduleRepository.findById(scheduleIdObj)
                .orElseThrow(() -> new GeneralException(ErrorStatus._SCHEDULE_NOT_FOUND));

        // 동아리에 속한 스케줄인지 확인
        if (!club.getId().equals(schedule.getClub().getId())) {
            throw new GeneralException(ErrorStatus._SCHEDULE_IS_NOT_BELONG_TO_CLUB);
        }

        // 새로운 스케줄 아이디 생성
        Integer newMonth = clubScheduleDTO.getMonthToChange() != null ? clubScheduleDTO.getMonthToChange() : month;

        ScheduleId newScheduleId = ScheduleId.builder()
                .clubId(clubId)
                .month(newMonth)
                .build();

        scheduleRepository.deleteById(scheduleIdObj); // 기존 스케줄 삭제

        // 새로운 스케줄 생성
        Schedule newSchedule = Schedule.builder()
                .club(club)
                .id(newScheduleId)
                .content(clubScheduleDTO.getContent())
                .build();

        scheduleRepository.save(newSchedule);

        return newSchedule.getId();
    }

    @Override
    public void deleteClubSchedule(Long clubId, Integer month) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        ScheduleId scheduleIdObj = ScheduleId.builder()
                .clubId(clubId)
                .month(month)
                .build();

        Schedule schedule = scheduleRepository.findById(scheduleIdObj)
                .orElseThrow(() -> new GeneralException(ErrorStatus._SCHEDULE_NOT_FOUND));

        if (!club.getId().equals(schedule.getClub().getId())) {
            throw new GeneralException(ErrorStatus._SCHEDULE_ALREADY_EXISTS);
        }
        scheduleRepository.deleteById(scheduleIdObj); // 기존 스케줄 삭제
    }

    @Override
    public Long saveClubIntroduction(Long clubId, ClubIntroductionDTO clubIntroductionDTO) {

        if (!clubRepository.existsById(clubId)) {
            throw new GeneralException(ErrorStatus._CLUB_NOT_FOUND);
        }

        // 기존 Introduction 조회
        Introduction introduction = introductionRepository.findById(clubId)
                .orElseGet(() -> Introduction.builder().clubId(clubId).build());

        // Introduction 내용 업데이트
        introduction.updateIntroduction(clubIntroductionDTO.getIntroduction(),
                clubIntroductionDTO.getActivity(), clubIntroductionDTO.getRecruitment());

        // 저장
        Introduction saved = introductionRepository.save(introduction);

        return saved.getClubId();
    }


    @Override
    public Long saveClubRecruitment(Long clubId, ClubRecruitmentDTO clubRecruitmentDTO) {

        if (!clubRepository.existsById(clubId)) {
            throw new GeneralException(ErrorStatus._CLUB_NOT_FOUND);
        }

        Recruitment recruitment = recruitmentRepository.findById(clubId)
                .orElseGet(() -> Recruitment.builder().clubId(clubId).build());

        recruitment.updateRecruitment(clubRecruitmentDTO);

        Recruitment save = recruitmentRepository.save(recruitment);

        return save.getClubId();
    }
}
