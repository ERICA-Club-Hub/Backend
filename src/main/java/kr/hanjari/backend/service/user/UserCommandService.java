package kr.hanjari.backend.service.user;

import jakarta.servlet.http.HttpServletResponse;
import kr.hanjari.backend.web.dto.user.request.UserLoginRequestDTO;
import kr.hanjari.backend.web.dto.user.response.UserCodeResponseDTO;
import kr.hanjari.backend.web.dto.user.response.UserLoginResponseDTO;

public interface UserCommandService {

    // 동아리 별 인증 코드 생성
    UserCodeResponseDTO createCode(String clubName);

    // 인증 코드를 통한 로그인
    UserLoginResponseDTO login(UserLoginRequestDTO code, HttpServletResponse response);

    // 로그아웃
    void logout(String authorizationHeader);

}
