package kr.hanjari.backend.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;
import kr.hanjari.backend.global.payload.code.status.ErrorStatus;
import kr.hanjari.backend.global.payload.exception.GeneralException;

public class WebUtil {

    private WebUtil() {}

    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null) {
            return null;
        }
        if (bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            throw new GeneralException(ErrorStatus._INVALID_TOKEN_FORMAT);
        }

    }
}
