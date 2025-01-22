package kr.hanjari.backend.payload.exception.handler;

import kr.hanjari.backend.payload.code.BaseErrorCode;
import kr.hanjari.backend.payload.exception.GeneralException;

public class RecruitmentHandler extends GeneralException {

    public RecruitmentHandler(BaseErrorCode code) {
        super(code);
    }
}
