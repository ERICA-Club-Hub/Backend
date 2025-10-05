package kr.hanjari.backend.domain.auth.application.service.impl;

import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;
import kr.hanjari.backend.domain.club.domain.repository.ClubRepository;
import kr.hanjari.backend.infrastructure.jwt.JwtUtil;
import kr.hanjari.backend.domain.auth.application.service.AuthService;
import kr.hanjari.backend.domain.auth.presentation.dto.LoginResultDTO;
import kr.hanjari.backend.domain.auth.presentation.dto.request.LoginRequestDTO;
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
            token = jwtUtil.createUnionAdminToken();
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
