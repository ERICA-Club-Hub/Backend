package kr.hanjari.backend.payload.exception.handler;

import kr.hanjari.backend.payload.code.BaseErrorCode;
import kr.hanjari.backend.payload.exception.GeneralException;

public class IntroductionHandler extends GeneralException {

    public IntroductionHandler(BaseErrorCode code) {
        super(code);
    }
}
