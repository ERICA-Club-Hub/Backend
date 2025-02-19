package kr.hanjari.backend.service.club;

import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubUtil {

    private final ClubRepository clubRepository;

    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // 코드에 사용할 문자 집합
    private static final int CODE_LENGTH = 6; // 코드 길이

    private final Random random = new SecureRandom();

    public String reissueClubCode(Long clubId) {
        String code;
        boolean isUnique;

        do {
            code = generateRandomCode();
            isUnique = !clubRepository.existsByCode(code);
        } while (!isUnique);

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._CLUB_NOT_FOUND));
        club.updateCode(code);

        return code;
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
