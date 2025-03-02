package kr.hanjari.backend.service.auth.impl;

import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.repository.ClubRepository;
import kr.hanjari.backend.security.token.JwtUtil;
import kr.hanjari.backend.service.auth.AuthService;
import kr.hanjari.backend.web.dto.auth.LoginResultDTO;
import kr.hanjari.backend.web.dto.auth.request.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ClubRepository clubRepository;
    private final JwtUtil jwtUtil;

    @Value("${service.admin}")
    private String SERVICE_ADMIN_CODE;
    @Value("${union.admin}")
    private String UNION_ADMIN_CODE;

    @Override
    public LoginResultDTO login(LoginRequestDTO request) {
        String code = request.code();
        String token;

        if (code.equals(SERVICE_ADMIN_CODE)) {
            token = jwtUtil.createServiceAdminToken();
            return LoginResultDTO.of(token, 0L, "Service Admin");
        }
        if (code.equals(UNION_ADMIN_CODE)) {
            token = jwtUtil.createAdminToken();
            return LoginResultDTO.of(token, 0L, "Union Admin");
        }

        Club club = clubRepository.findByCode(code)
                .orElseThrow(() -> new GeneralException(ErrorStatus._INVALID_CODE));
        Long clubId = club.getId();
        String clubName = club.getName();
        token = jwtUtil.createClubAdminToken(clubId);

        return LoginResultDTO.of(token, clubId, clubName);
    }

    @Override
    public void logout(String token) {
        jwtUtil.addTokenToBlacklist(token);
    }
}
