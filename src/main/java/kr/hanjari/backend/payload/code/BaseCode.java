package kr.hanjari.backend.payload.code;

import kr.hanjari.backend.payload.dto.ReasonDTO;

public interface BaseCode {

    ReasonDTO getReason();

    ReasonDTO getReasonHttpStatus();

}
