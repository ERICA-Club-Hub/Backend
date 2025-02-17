package kr.hanjari.backend.service.auth.impl;

import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.payload.code.status.ErrorStatus;
import kr.hanjari.backend.payload.exception.GeneralException;
import kr.hanjari.backend.repository.ClubRepository;
import kr.hanjari.backend.security.token.JwtUtil;
import kr.hanjari.backend.service.auth.AuthService;
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
    @Value("${admin}")
    private String ADMIN_CODE;

    @Override
    public String login(LoginRequestDTO request) {
        String code = request.code();

        if (code.equals(SERVICE_ADMIN_CODE)) {
            return jwtUtil.createServiceAdminToken();
        }
        if (code.equals(ADMIN_CODE)) {
            return jwtUtil.createAdminToken();
        }

        Club club = clubRepository.findByCode(code)
                .orElseThrow(() -> new GeneralException(ErrorStatus._INVALID_CODE));
        return jwtUtil.createClubAdminToken(club.getId());
    }

    @Override
    public void logout(String token) {
        jwtUtil.addTokenToBlacklist(token);
    }
}
