package kr.hanjari.backend.service.club.impl;


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

    @Override
    public Long saveClubDetail(ClubDetailDTO clubDetailDTO) {
        return 0;
    }

    @Override
    public Long updateClubDetail(Long clubId, ClubDetailDTO clubDetailDTO) {
        return 0;
    }

    @Override
    public void deleteClubDetail(Long clubId) {

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
    public Long updateClubIntroduction(Long clubId, ClubIntroductionDTO introduction) {
        return 0;
    }

    @Override
    public void deleteClubIntroduction(Long clubId) {

    }

    @Override
    public Long saveClubRecruitment(Long clubId, ClubRecruitmentDTO recruitment) {
        return 0;
    }

    @Override
    public Long updateClubRecruitment(Long clubId, ClubRecruitmentDTO recruitment) {
        return 0;
    }

    @Override
    public void deleteClubRecruitment(Long clubId) {

    }
}
