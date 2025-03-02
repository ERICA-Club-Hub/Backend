package kr.hanjari.backend.service.auth;

import kr.hanjari.backend.web.dto.auth.LoginResultDTO;
import kr.hanjari.backend.web.dto.auth.request.LoginRequestDTO;

public interface AuthService {

    public LoginResultDTO login(LoginRequestDTO request);
    public void logout(String token);
}
