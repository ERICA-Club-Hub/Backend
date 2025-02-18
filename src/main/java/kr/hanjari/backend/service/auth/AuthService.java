package kr.hanjari.backend.service.auth;

import kr.hanjari.backend.web.dto.auth.request.LoginRequestDTO;

public interface AuthService {

    public String login(LoginRequestDTO request);
    public void logout(String token);
}
