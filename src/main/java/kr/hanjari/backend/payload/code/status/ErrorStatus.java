package kr.hanjari.backend.payload.code.status;

import kr.hanjari.backend.payload.code.BaseErrorCode;
import kr.hanjari.backend.payload.dto.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    //일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의해주세요."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 사용자 관련
    _TOKEN_ALREADY_LOGOUT(HttpStatus.BAD_REQUEST, "USER404", "이미 로그아웃 처리된 토큰입니다."),
    _TOKEN_NOT_EXIST(HttpStatus.UNAUTHORIZED, "USER401", "토큰이 존재하지 않습니다."),
    _TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "USER401", "만료된 토큰입니다."),

    // 동아리 관련
    _CLUB_NOT_FOUND(HttpStatus.NOT_FOUND, "CLUB404", "동아리를 찾을 수 없습니다."),
    _INVALID_CODE(HttpStatus.BAD_REQUEST, "CLUB400", "유효하지 않은 코드입니다."),

    _SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "ACTIVITY404", "활동을 찾을 수 없습니다."),
    _SCHEDULE_IS_NOT_BELONG_TO_CLUB(HttpStatus.BAD_REQUEST, "ACTIVITY400", "해당 동아리에 속한 활동이 아닙니다."),
    _SCHEDULE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "ACTIVITY400", "해당 월에 활동이 이미 존재합니다."),
    _SCHEDULE_TO_CHANGE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "ACTIVITY400", "변경할 월에 활동이 이미 존재합니다."),

    _INTRODUCTION_NOT_FOUND(HttpStatus.NOT_FOUND, "INTRODUCTION404", "소개를 찾을 수 없습니다."),

    _SERVICE_ANNOUNCEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "SERVICE_ANNOUNCEMENT404", "공지사항을 찾을 수 없습니다."),;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getErrorReason() {
        return ErrorReasonDTO.builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }

    @Override
    public ErrorReasonDTO getErrorReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .httpStatus(httpStatus)
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }
}