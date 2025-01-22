package kr.hanjari.backend.service.club.impl;


import kr.hanjari.backend.domain.Activity;
import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.handler.ActivityHandler;
import kr.hanjari.backend.payload.exception.handler.ClubHandler;
import kr.hanjari.backend.repository.ActivityRepository;
import kr.hanjari.backend.repository.ClubRepository;
import kr.hanjari.backend.repository.IntroductionRepository;
import kr.hanjari.backend.repository.RecruitmentRepository;
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
    private final ActivityRepository activityRepository;

    @Override
    public Long saveClubDetail(Long clubId, ClubDetailDTO clubDetailDTO) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new ClubHandler(ErrorStatus._CLUB_NOT_FOUND));
        club.updateClubDetails(clubDetailDTO);
        Club saved = clubRepository.save(club);
        return saved.getId();
    }

    @Override
    public Long saveClubActivity(Long clubId, ClubActivityDTO clubActivityDTO) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new ClubHandler(ErrorStatus._CLUB_NOT_FOUND));

        Activity activity = Activity.builder()
                .month(clubActivityDTO.getMonth())
                .content(clubActivityDTO.getActivity())
                .club(club)
                .build();

        Activity save = activityRepository.save(activity);
        return save.getId();
    }

    @Override
    public Long updateClubActivity(Long clubId, Long activityId, ClubActivityDTO clubActivityDTO) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new ClubHandler(ErrorStatus._CLUB_NOT_FOUND));

        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ActivityHandler(ErrorStatus._ACTIVITY_NOT_FOUND));

        if (!club.getId().equals(activity.getClub().getId())) {
            throw new ActivityHandler(ErrorStatus._ACITVITY_IS_NOT_BELONG_TO_CLUB);
        }

        activity.updateActivity(clubActivityDTO);
        activityRepository.save(activity);
        return activity.getId();
    }

    @Override
    public void deleteClubActivity(Long clubId, Long activityId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new ClubHandler(ErrorStatus._CLUB_NOT_FOUND));

        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ActivityHandler(ErrorStatus._ACTIVITY_NOT_FOUND));

        if (!club.getId().equals(activity.getClub().getId())) {
            throw new ActivityHandler(ErrorStatus._ACITVITY_IS_NOT_BELONG_TO_CLUB);
        }

        activityRepository.delete(activity);
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
