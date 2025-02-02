package kr.hanjari.backend.service.club.impl;

import java.util.Comparator;
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
import kr.hanjari.backend.repository.specification.ClubSpecifications;
import kr.hanjari.backend.service.club.ClubQueryService;
import kr.hanjari.backend.web.dto.club.response.ClubIntroductionResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubRecruitmentResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubScheduleResponseDTO;
import kr.hanjari.backend.web.dto.club.response.ClubSearchResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ClubSearchResponseDTO findClubsByCondition(
            String name, ClubCategory category, RecruitmentStatus status, SortBy sortBy, int page,
            int size) {

        if (sortBy != null && sortBy.equals(SortBy.RECRUITMENT_STATUS_ASC)) {
            Page<Club> clubs = clubRepository.findClubsOrderByRecruitmentStatus(name, category, status, PageRequest.of(page, size));
            return ClubSearchResponseDTO.of(clubs);
        }

        Sort sort = (sortBy != null) ? sortBy.getSort() : SortBy.NAME_ASC.getSort();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Club> clubs = clubRepository.findAll(ClubSpecifications.findByCondition(name, category, status), pageable);
        return ClubSearchResponseDTO.of(clubs);
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

    private Comparator<RecruitmentStatus> recruitmentStatusComparator =
            Comparator.comparingInt(status -> switch (status) {
                case OPEN -> 0;
                case UPCOMING -> 1;
                case CLOSED -> 2;
            });

}
