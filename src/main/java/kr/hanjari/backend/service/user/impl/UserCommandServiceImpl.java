package kr.hanjari.backend.service.user.impl;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.repository.ClubRepository;
import kr.hanjari.backend.service.auth.JwtTokenProvider;
import kr.hanjari.backend.service.user.UserCommandService;
import kr.hanjari.backend.web.dto.user.UserRequestDTO;
import kr.hanjari.backend.web.dto.user.UserResponseDTO.UserCodeDTO;
import kr.hanjari.backend.web.dto.user.UserResponseDTO.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final ClubRepository clubRepository;
    private final StringRedisTemplate redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    private static final String BLACKLIST_KEY = "blacklist";

    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // 코드에 사용할 문자 집합
    private static final int CODE_LENGTH = 6; // 코드 길이
    private final SecureRandom random = new SecureRandom();

    @Override
    public UserCodeDTO createCode(String clubName) {
        Club club = clubRepository.findByName(clubName).orElseThrow(
                () -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        String code; boolean isUnique;

        do {
            code = generateRandomCode();
            isUnique = !clubRepository.existsByCode(code);
        } while (!isUnique);

        club.updateCode(code);
        clubRepository.save(club);

        return UserCodeDTO.of(code, clubName);
    }

    @Override
    public UserLoginDTO login(UserRequestDTO.UserLoginDTO request) {
        Club club = clubRepository.findByCode(request.getCode()).orElseThrow(
                () -> new GeneralException(ErrorStatus._INVALID_CODE));

        // TODO: JWT 토큰 발급
        String accessToken = jwtTokenProvider.createToken(club.getName());

        return UserLoginDTO.of(accessToken, club.getName(), request.getCode());
    }

    @Override
    public void logout(String authorizationHeader) {
        String token = authorizationHeader.substring(7); // "Bearer " 제거
        if (jwtTokenProvider.isTokenExpired(token))  {
            return; // 만료된 토큰은 블랙리스트에 추가하지 않음
        }
        if (isContainToken(token)) {
            throw new GeneralException(ErrorStatus._TOKEN_ALREADY_LOGOUT);
        }
        // Redis 블랙리스트에 토큰 추가
        redisTemplate.opsForSet().add(BLACKLIST_KEY, token);
        // 예시로 만료시간 설정 (1일)
        redisTemplate.expire(BLACKLIST_KEY, 1, TimeUnit.DAYS);
    }

    @Override
    public boolean isContainToken(String token) {
        return redisTemplate.opsForSet().isMember(BLACKLIST_KEY, token);
    }

    @Override
    public String getClubNameFromToken(String token) {
        return jwtTokenProvider.getClubNameFromToken(token);
    }

    private String generateRandomCode() {
        StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHAR_POOL.length());
            codeBuilder.append(CHAR_POOL.charAt(index));
        }
        return codeBuilder.toString();
    }
}
