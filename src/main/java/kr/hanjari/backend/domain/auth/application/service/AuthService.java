package kr.hanjari.backend.domain.auth.application.service;

import kr.hanjari.backend.domain.auth.presentation.dto.LoginResultDTO;
import kr.hanjari.backend.domain.auth.presentation.dto.request.LoginRequestDTO;

public interface AuthService {

    public LoginResultDTO login(LoginRequestDTO request);
    public void logout(String token);
}
