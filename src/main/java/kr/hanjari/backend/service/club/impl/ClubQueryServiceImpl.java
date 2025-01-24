package kr.hanjari.backend.service.club.impl;

import java.util.List;
import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.Introduction;
import kr.hanjari.backend.domain.Recruitment;
import kr.hanjari.backend.domain.Schedule;
import kr.hanjari.backend.domain.enums.ClubCategory;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.enums.SortBy;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.repository.ClubRepository;
import kr.hanjari.backend.repository.IntroductionRepository;
import kr.hanjari.backend.repository.RecruitmentRepository;
import kr.hanjari.backend.repository.ScheduleRepository;
import kr.hanjari.backend.service.club.ClubQueryService;
import kr.hanjari.backend.web.dto.club.ClubResponseDTO.ClubScheduleDTO;
import kr.hanjari.backend.web.dto.club.ClubResponseDTO.ClubDTO;
import kr.hanjari.backend.web.dto.club.ClubResponseDTO.ClubIntroductionDTO;
import kr.hanjari.backend.web.dto.club.ClubResponseDTO.ClubRecruitmentDTO;
import kr.hanjari.backend.web.dto.club.ClubResponseDTO.ClubSearchDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubQueryServiceImpl implements ClubQueryService {

    private final ClubRepository clubRepository;
    private final IntroductionRepository introductionRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ScheduleRepository scheduleRepository;


    @Override
    public ClubSearchDTO findClubsByCondition(String name, ClubCategory category, RecruitmentStatus status, SortBy sortBy, int page,
                                              int size) {
        return null;
    }

    @Override
    public ClubDTO findClubDetail(Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        return ClubDTO.of(club);
    }

    @Override
    public ClubScheduleDTO findAllClubActivities(Long clubId) {
        if (!clubRepository.existsById(clubId)) {
            throw new GeneralException(ErrorStatus._CLUB_NOT_FOUND);
        }

        List<Schedule> schedules = scheduleRepository.findAllByClubId(clubId);

        return ClubScheduleDTO.of(schedules);
    }

    @Override
    public ClubIntroductionDTO findClubIntroduction(Long clubId) {
        if (!clubRepository.existsById(clubId)) {
            throw new GeneralException(ErrorStatus._CLUB_NOT_FOUND);
        }

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        Introduction introduction = introductionRepository.findByClubId(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._INTRODUCTION_NOT_FOUND));


        return ClubIntroductionDTO.of(introduction, club);
    }

    @Override
    public ClubRecruitmentDTO findClubRecruitment(Long clubId) {

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        Recruitment recruitment = recruitmentRepository.findByClubId(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._INTRODUCTION_NOT_FOUND));


        return ClubRecruitmentDTO.of(recruitment, club);
    }
}
