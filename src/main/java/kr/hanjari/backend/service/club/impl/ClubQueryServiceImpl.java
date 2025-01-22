package kr.hanjari.backend.service.club.impl;

import kr.hanjari.backend.service.club.ClubQueryService;
import kr.hanjari.backend.web.dto.club.ClubResponseDTO.ClubActivityDTO;
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

    @Override
    public ClubSearchDTO findClubsByCondition(String name, String category, String status, String sortBy, int page,
                                              int size) {
        return null;
    }

    @Override
    public ClubDetailDTO findClubDetail(Long clubId) {
        return null;
    }

    @Override
    public ClubActivityDTO findAllClubActivities(Long clubId, int page, int size) {
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
