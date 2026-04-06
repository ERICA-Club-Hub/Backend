package kr.hanjari.backend.domain.club.application.query.impl;

import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.domain.repository.ClubRepository;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile({"local", "dev"})
@Service
@RequiredArgsConstructor
@Transactional
public class ClubViewCountTestService {

    private final ClubRepository clubRepository;

    private final StringRedisTemplate redisTemplate;


    public ClubDetailResponse findClubDetailWithDirtyCheck(Long clubId) {
        Club club = getClub(clubId);
        club.incrementViewCount();
        return getDTO(club);
    }

    public ClubDetailResponse findClubDetailWithPessimisticLock(Long clubId) {
        Club club = getClubWithXLock(clubId);
        club.incrementViewCount();
        return getDTO(club);
    }

//    public ClubDetailResponse findClubDetailWithOptimisticLock(Long clubId) {
//        Club club = getClub(clubId);
//
//        int updated = clubRepository.incrementViewCountWithVersionCheck(clubId, club.getVersion());
//
//        if (updated == 0) {
//            throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
//        }
//        return getDTO(club);
//    }

    public ClubDetailResponse findClubDetailWithAtomicOperation(Long clubId) {
        Club club = getClub(clubId);

        clubRepository.incrementViewCount(clubId);
        return getDTO(club);
    }

    public ClubDetailResponse findClubDetailWithRedis(Long clubId) {
        Club club = getClub(clubId);

        String key = "club:viewCount:" + clubId;
        redisTemplate.opsForValue().increment(key);

        return getDTO(club);
    }

    private ClubDetailResponse getDTO(Club club) {
        return ClubDetailResponse.from(club);
    }

    private Club getClub(Long clubId) {
        return clubRepository.findById(clubId)
                .orElse(null);
    }

    private Club getClubWithXLock(Long clubId) {
        return clubRepository.findByIdWithPessimisticLock(clubId)
                .orElse(null);
    }

}
