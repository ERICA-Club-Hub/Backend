package kr.hanjari.backend.payload.exception.handler;

import kr.hanjari.backend.payload.code.BaseErrorCode;
import kr.hanjari.backend.payload.exception.GeneralException;

public class ScheduleHandler extends GeneralException {

    public ScheduleHandler(BaseErrorCode code) {
        super(code);
    }
}
