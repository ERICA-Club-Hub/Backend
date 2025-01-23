package kr.hanjari.backend.service.club.impl;


import kr.hanjari.backend.domain.Activity;
import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.Introduction;
import kr.hanjari.backend.domain.Recruitment;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
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
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));
        club.updateClubDetails(clubDetailDTO);
        Club saved = clubRepository.save(club);
        return saved.getId();
    }

    @Override
    public Long saveClubActivity(Long clubId, ClubActivityDTO clubActivityDTO) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

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
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new GeneralException(ErrorStatus._ACTIVITY_NOT_FOUND));

        if (!club.getId().equals(activity.getClub().getId())) {
            throw new GeneralException(ErrorStatus._ACITVITY_IS_NOT_BELONG_TO_CLUB);
        }

        activity.updateActivity(clubActivityDTO);
        activityRepository.save(activity);
        return activity.getId();
    }

    @Override
    public void deleteClubActivity(Long clubId, Long activityId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new GeneralException(ErrorStatus._ACTIVITY_NOT_FOUND));

        if (!club.getId().equals(activity.getClub().getId())) {
            throw new GeneralException(ErrorStatus._ACITVITY_IS_NOT_BELONG_TO_CLUB);
        }

        activityRepository.delete(activity);
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
        introduction.updateIntroduction(clubIntroductionDTO);

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
