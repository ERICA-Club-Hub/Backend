package kr.hanjari.backend.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class UserResponseDTO {


    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLoginDTO {
        private String accessToken;
        private String clubName;
        private String code;

        public static UserLoginDTO of(String accessToken, String clubName, String code) {
            return UserLoginDTO.builder()
                .accessToken(accessToken)
                .clubName(clubName)
                .code(code)
                .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserCodeDTO {
        private String code;
        private String clubName;

        public static UserCodeDTO of(String code, String clubName) {
            return UserCodeDTO.builder()
                .code(code)
                .clubName(clubName)
                .build();
        }
    }
}
