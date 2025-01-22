package kr.hanjari.backend.service.club.impl;


import kr.hanjari.backend.domain.Activity;
import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.Introduction;
import kr.hanjari.backend.domain.Recruitment;
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
    public Long saveClubIntroduction(Long clubId, ClubIntroductionDTO clubIntroductionDTO) {

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new ClubHandler(ErrorStatus._CLUB_NOT_FOUND));

        // 동아리 소개가 이미 존재한다면 삭제
        introductionRepository.findById(clubId).ifPresent(introductionRepository::delete);

        Introduction introduction = Introduction.builder()
                .clubId(clubId)
                .club(club)
                .content1(clubIntroductionDTO.getIntroduction())
                .content2(clubIntroductionDTO.getActivity())
                .content3(clubIntroductionDTO.getRecruitment())
                .build();

        Introduction save = introductionRepository.save(introduction);

        return save.getClubId();
    }

    @Override
    public Long saveClubRecruitment(Long clubId, ClubRecruitmentDTO clubRecruitmentDTO) {

        Club club = clubRepository.findById(clubId).orElseThrow(() -> new ClubHandler(ErrorStatus._CLUB_NOT_FOUND));

        // 동아리 모집 안내가 이미 존재한다면 삭제
        recruitmentRepository.findById(clubId).ifPresent(recruitmentRepository::delete);

        Recruitment recruitment = Recruitment.builder()
                .clubId(clubId)
                .club(club)
                .content1(clubRecruitmentDTO.getDue())
                .content2(clubRecruitmentDTO.getNotice())
                .content3(clubRecruitmentDTO.getEtc())
                .build();

        Recruitment save = recruitmentRepository.save(recruitment);

        return save.getClubId();
    }
}
