package kr.hanjari.backend.service.user.impl;

import jakarta.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.repository.ClubRepository;
import kr.hanjari.backend.security.auth.JwtTokenProvider;
import kr.hanjari.backend.service.user.UserCommandService;
import kr.hanjari.backend.web.dto.user.request.UserLoginRequestDTO;
import kr.hanjari.backend.web.dto.user.response.UserCodeResponseDTO;
import kr.hanjari.backend.web.dto.user.response.UserLoginResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final ClubRepository clubRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${service.admin}")
    private String SERVICE_ADMIN_CODE;
    @Value("${admin}")
    private String ADMIN_CODE;

    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // 코드에 사용할 문자 집합
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String SERVICE_ADMIN = "서비스 관리자";
    private static final String ADMIN = "총동아리연합회";
    private static final String BEARER = "Bearer ";
    private static final int CODE_LENGTH = 6; // 코드 길이

    private final SecureRandom random = new SecureRandom();

    @Override
    public UserCodeResponseDTO createCode(String clubName) {
        Club club = clubRepository.findByName(clubName).orElseThrow(
                () -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));

        String code; boolean isUnique;

        do {
            code = generateRandomCode();
            isUnique = !clubRepository.existsByCode(code);
        } while (!isUnique);

        club.updateCode(code);
        clubRepository.save(club);

        return UserCodeResponseDTO.of(code, clubName);
    }

    @Override
    public UserLoginResponseDTO login(UserLoginRequestDTO request, HttpServletResponse response) {
        if (request.code().equals(SERVICE_ADMIN_CODE)) {
            response.setHeader(AUTHORIZATION_HEADER, BEARER + jwtTokenProvider.createServiceAdminToken());
            return UserLoginResponseDTO.of(SERVICE_ADMIN, 0L);
        }

        if (request.code().equals(ADMIN_CODE)) {
            response.setHeader(AUTHORIZATION_HEADER, BEARER + jwtTokenProvider.createAdminToken());
            return UserLoginResponseDTO.of(ADMIN, 0L);
        }

        Club club = clubRepository.findByCode(request.code()).orElseThrow(
                () -> new GeneralException(ErrorStatus._INVALID_CODE));

        String accessToken = jwtTokenProvider.createToken(club.getName());

        // 헤더에 토큰 포함
        response.setHeader(AUTHORIZATION_HEADER, BEARER  + accessToken);
        return UserLoginResponseDTO.of(club.getName(), club.getId());
    }

    @Override
    public void logout(String authorizationHeader) {
        String token = authorizationHeader.substring(7); // "Bearer " 제거
        if (jwtTokenProvider.isTokenInBlacklist(token)) {
            throw new GeneralException(ErrorStatus._TOKEN_ALREADY_LOGOUT);
        }
        jwtTokenProvider.addTokenToBlacklist(token);
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
