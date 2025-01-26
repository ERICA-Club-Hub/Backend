package kr.hanjari.backend.service.user.impl;

import java.security.SecureRandom;
import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.repository.ClubRepository;
import kr.hanjari.backend.service.user.UserCommandService;
import kr.hanjari.backend.web.dto.user.UserResponseDTO.UserCodeDTO;
import kr.hanjari.backend.web.dto.user.UserResponseDTO.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final ClubRepository clubRepository;

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
            isUnique = clubRepository.existsByCode(code);
        } while (!isUnique);

        club.updateCode(code);
        clubRepository.save(club);

        return UserCodeDTO.of(code, clubName);
    }

    @Override
    public UserLoginDTO login(String code) {
        Club club = clubRepository.findByCode(code).orElseThrow(
                () -> new GeneralException(ErrorStatus._INVALID_CODE));

        // TODO: JWT 토큰 발급


        return UserLoginDTO.of("accessToken", club.getName(), code);
    }

    @Override
    public void logout() {
        // TODO: 로그아웃 기능 구현

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
