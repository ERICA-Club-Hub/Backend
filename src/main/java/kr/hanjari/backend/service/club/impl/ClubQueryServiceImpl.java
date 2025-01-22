package kr.hanjari.backend.service.club.impl;

import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.enums.ClubCategory;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.enums.SortBy;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.handler.ClubHandler;
import kr.hanjari.backend.repository.ActivityRepository;
import kr.hanjari.backend.repository.ClubRepository;
import kr.hanjari.backend.repository.IntroductionRepository;
import kr.hanjari.backend.repository.RecruitmentRepository;
import kr.hanjari.backend.service.club.ClubQueryService;
import kr.hanjari.backend.web.dto.club.ClubResponseDTO.ClubActivityDTO;
import kr.hanjari.backend.web.dto.club.ClubResponseDTO.ClubDTO;
import kr.hanjari.backend.web.dto.club.ClubResponseDTO.ClubDetailDTO;
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
    private final ActivityRepository activityRepository;


    @Override
    public ClubSearchDTO findClubsByCondition(String name, ClubCategory category, RecruitmentStatus status, SortBy sortBy, int page,
                                              int size) {
        return null;
    }

    @Override
    public ClubDetailDTO findClubDetail(Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new ClubHandler(ErrorStatus._CLUB_NOT_FOUND));

        return ClubDetailDTO.builder()
                .club(ClubDTO.builder()
                        .id(club.getId())
                        .name(club.getName())
                        .description(club.getBriefIntroduction())
                        .category(club.getCategory())
                        .recruitmentStatus(club.getRecruitmentStatus())
                        .build())
                //.profileImageUrl(club.getProfileImageUrl())
                .leaderName(club.getLeaderName())
                .leaderPhone(club.getLeaderPhone())
                .leaderEmail(club.getLeaderEmail())
                .activities(club.getMeetingSchedule())
                .snsUrl(club.getSnsUrl())
                .applicationUrl(club.getApplicationUrl())
                .build();
    }

    @Override
    public ClubActivityDTO findAllClubActivities(Long clubId) {
        return null;
    }

    @Override
    public ClubIntroductionDTO findClubIntroduction(Long clubId) {
        return null;
    }

    @Override
    public ClubRecruitmentDTO findClubRecruitment(Long clubId) {
        return null;
    }
}
