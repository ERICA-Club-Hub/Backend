package kr.hanjari.backend.service.club.impl;


import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.Introduction;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.handler.ClubHandler;
import kr.hanjari.backend.repository.ClubRepository;
import kr.hanjari.backend.repository.IntroductionRepository;
import kr.hanjari.backend.repository.RecruitmentRepository;
import kr.hanjari.backend.repository.ScheduleRepository;
import kr.hanjari.backend.service.club.ClubCommandService;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubActivityDTO;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubDetailDTO;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubIntroductionDTO;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubRecruitmentDTO;
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
        return 0;
    }

    @Override
    public Long saveClubActivity(Long clubId, ClubActivityDTO clubActivityDTO) {
        return 0;
    }

    @Override
    public Long updateClubActivity(Long clubId, Long activityId, ClubActivityDTO clubActivityDTO) {
        return 0;
    }

    @Override
    public void deleteClubActivity(Long clubId, Long activityId) {

    }

    @Override
    public Long saveClubIntroduction(Long clubId, ClubIntroductionDTO introduction) {
        return 0;
    }

    @Override
    public Long saveClubRecruitment(Long clubId, ClubRecruitmentDTO recruitment) {
        return 0;
    }
}
