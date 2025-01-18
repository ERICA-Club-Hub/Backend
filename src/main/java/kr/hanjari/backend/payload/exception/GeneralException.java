package kr.hanjari.backend.payload.exception;

import kr.hanjari.backend.payload.code.BaseErrorCode;
import kr.hanjari.backend.payload.dto.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
        return this.code.getErrorReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus() {
        return this.code.getErrorReasonHttpStatus();
    }
}

