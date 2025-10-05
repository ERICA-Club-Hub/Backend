package kr.hanjari.backend.global.payload.code;

import kr.hanjari.backend.global.payload.dto.ErrorReasonDTO;

public interface BaseErrorCode {

    ErrorReasonDTO getErrorReason();

    ErrorReasonDTO getErrorReasonHttpStatus();
}