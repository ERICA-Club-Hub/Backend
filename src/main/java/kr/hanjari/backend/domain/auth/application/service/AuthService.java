package kr.hanjari.backend.domain.auth.application.service;

import kr.hanjari.backend.domain.auth.presentation.dto.LoginResultDTO;
import kr.hanjari.backend.domain.auth.presentation.dto.request.LoginRequest;

public interface AuthService {

    public LoginResultDTO login(LoginRequest request);
    public void logout(String token);
}
