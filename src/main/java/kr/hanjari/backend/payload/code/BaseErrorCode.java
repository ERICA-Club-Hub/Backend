package kr.hanjari.backend.payload.code;

import kr.hanjari.backend.payload.dto.ErrorReasonDTO;

public interface BaseErrorCode {

    ErrorReasonDTO getErrorReason();

    ErrorReasonDTO getErrorReasonHttpStatus();
}