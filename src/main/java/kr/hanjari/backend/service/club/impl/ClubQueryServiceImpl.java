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
import kr.hanjari.backend.web.dto.club.response.ClubIntroductionResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubRecruitmentResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubScheduleResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubSearchResponseDTO;
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
    public ClubSearchResponseDTO findClubsByCondition(String name, ClubCategory category, RecruitmentStatus status, SortBy sortBy, int page,
                                                      int size) {
        return null;
    }

    @Override
    public ClubResponseDTO findClubDetail(Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        return ClubResponseDTO.of(club);
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
    public ClubIntroductionResponseDTO findClubIntroduction(Long clubId) {
        if (!clubRepository.existsById(clubId)) {
            throw new GeneralException(ErrorStatus._CLUB_NOT_FOUND);
        }

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        Introduction introduction = introductionRepository.findByClubId(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._INTRODUCTION_NOT_FOUND));


        return ClubIntroductionResponseDTO.of(club, introduction);
    }

    @Override
    public ClubRecruitmentResponseDTO findClubRecruitment(Long clubId) {

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        Recruitment recruitment = recruitmentRepository.findByClubId(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._INTRODUCTION_NOT_FOUND));


        return ClubRecruitmentResponseDTO.of(recruitment, club);
    }
}
