package kr.hanjari.backend.payload.exception.handler;

import kr.hanjari.backend.payload.code.BaseErrorCode;
import kr.hanjari.backend.payload.exception.GeneralException;

public class ActivityHandler extends GeneralException {

    public ActivityHandler(BaseErrorCode code) {
        super(code);
    }
}
