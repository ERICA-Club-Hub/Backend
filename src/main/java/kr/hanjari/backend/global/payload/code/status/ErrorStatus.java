package kr.hanjari.backend.global.payload.code.status;

import kr.hanjari.backend.global.payload.code.BaseErrorCode;
import kr.hanjari.backend.global.payload.dto.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의해주세요."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _INVALID_INPUT(HttpStatus.BAD_REQUEST, "MAJOR400", "유효하지 않은 입력입니다."),

    // 단과대 관련,
    _DEPARTMENT_IS_NOT_BELONG_TO_COLLEGE(HttpStatus.BAD_REQUEST, "MAJOR400", "해당 단과대에 속하지 않는 전공입니다."),

    // 사용자 관련
    _INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "USER401", "유효하지 않은 토큰입니다."),
    _TOKEN_ALREADY_LOGOUT(HttpStatus.BAD_REQUEST, "USER400", "이미 로그아웃 처리된 토큰입니다."),
    _TOKEN_NOT_EXIST(HttpStatus.UNAUTHORIZED, "USER401", "토큰이 존재하지 않습니다."),
    _TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "USER401", "만료된 토큰입니다."),
    _ACCESS_DENIED(HttpStatus.FORBIDDEN, "USER403", "접근 권한이 없습니다."),
    _INVALID_TOKEN_SIGNATURE(HttpStatus.UNAUTHORIZED, "USER401", "유효하지 않은 토큰 서명입니다."),
    _INVALID_TOKEN_FORMAT(HttpStatus.BAD_REQUEST, "USER400", "잘못된 토큰 형식입니다"),

    _FAIL_TO_SEND_EMAIL(HttpStatus.BAD_REQUEST, "CLUB400", "메일 전송이 실패하였습니다."),

    // 총동연 관련
    _UNION_ANNOUNCEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "UNION_ANNOUNCEMENT404", "공지사항을 찾을 수 없습니다."),

    // 파일 관련
    _FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE500", "파일 업로드에 실패했습니다."),
    _FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "FILE404", "파일을 찾을 수 없습니다."),
    _FILE_NAME_EMPTY(HttpStatus.BAD_REQUEST, "FILE400", "파일명이 비어있습니다."),

    // 자료 관련
    _DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "DATA404", "자료를 찾을 수 없습니다."),
    _DATA_EMPTY(HttpStatus.BAD_REQUEST, "DATA400", "파일을 업로드해주세요."),

    // S3 관련
    _S3_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "S3500", "오브젝트 업로드에 실패했습니다."),
    _S3_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "S3500", "오브젝트 삭제에 실패했습니다."),

    // 동아리 관련
    _CLUB_NOT_FOUND(HttpStatus.NOT_FOUND, "CLUB404", "동아리를 찾을 수 없습니다."),
    _INVALID_CODE(HttpStatus.BAD_REQUEST, "CLUB400", "유효하지 않은 코드입니다."),
    _CLUB_DETAIL_DRAFT_NOT_FOUND(HttpStatus.NOT_FOUND, "CLUB404", "임시 저장 된 동아리 상세 정보를 찾을 수 없습니다."),
    _CLUB_REGISTRATION_NOT_FOUND(HttpStatus.NOT_FOUND, "CLUBREGISTRATION404", "동아리 등록 요청을 찾을 수 없습니다."),

    _ACTIVITY_NOT_FOUND(HttpStatus.NOT_FOUND, "ACTIVITY404", "활동로그를 찾을 수 없습니다."),

    _SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "SCHEDULE404", "일정을 찾을 수 없습니다."),
    _SCHEDULE_IS_NOT_BELONG_TO_CLUB(HttpStatus.BAD_REQUEST, "SCHEDULE400", "해당 동아리에 속한 일정이 아닙니다."),
    _SCHEDULE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "SCHEDULE400", "해당 월에 일정이 이미 존재합니다."),
    _SCHEDULE_TO_CHANGE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "SCHEDULE400", "변경할 월에 일정이 이미 존재합니다."),
    _SCHEDULE_DESCRIPTION_DRAFT_NOT_FOUND(HttpStatus.NOT_FOUND, "SCHEDULE404", "임시 저장 된 일정 설명을 찾을 수 없습니다."),

    _INTRODUCTION_NOT_FOUND(HttpStatus.NOT_FOUND, "INTRODUCTION404", "소개를 찾을 수 없습니다."),
    _INTRODUCTION_DRAFT_NOT_FOUND(HttpStatus.NOT_FOUND, "INTRODUCTION404", "임시 저장 된 소개를 찾을 수 없습니다."),

    _RECRUITMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "RECRUITMENT404", "동아리 모집 안내를 찾을 수 없습니다."),
    _RECRUITMENT_DRAFT_NOT_FOUND(HttpStatus.NOT_FOUND, "RECRUITMENT404", "임시 저장 된 동아리 모집 안내를 찾을 수 없습니다."),

    _SERVICE_ANNOUNCEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "SERVICE_ANNOUNCEMENT404", "공지사항을 찾을 수 없습니다."),

    _FAQ_NOT_FOUND(HttpStatus.NOT_FOUND, "FAQ404", "FAQ를 찾을 수 없습니다.")

    ;
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