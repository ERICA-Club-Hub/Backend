package kr.hanjari.backend.global.payload.code;

import kr.hanjari.backend.global.payload.dto.ReasonDTO;

public interface BaseCode {

    ReasonDTO getReason();

    ReasonDTO getReasonHttpStatus();

}
