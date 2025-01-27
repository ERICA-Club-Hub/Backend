package kr.hanjari.backend.service.user;

import static kr.hanjari.backend.web.dto.user.UserResponseDTO.*;

import kr.hanjari.backend.web.dto.user.UserRequestDTO;
import kr.hanjari.backend.web.dto.user.UserResponseDTO;
import kr.hanjari.backend.web.dto.user.UserResponseDTO.UserLoginDTO;

public interface UserCommandService {

    // 동아리 별 인증 코드 생성
    UserCodeDTO createCode(String clubName);

    // 인증 코드를 통한 로그인
    UserLoginDTO login(UserRequestDTO.UserLoginDTO code);

    // 로그아웃
    void logout(String authorizationHeader);

    // 토큰이 블랙리스트에 있는지 확인
    boolean isContainToken(String token);

}
