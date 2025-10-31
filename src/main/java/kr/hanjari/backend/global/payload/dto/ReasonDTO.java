package kr.hanjari.backend.global.payload.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@Schema(description = "DTO for reason")
public class ReasonDTO {

    @Schema(description = "HTTP status code", nullable = false, example = "200")
    private HttpStatus httpStatus;
    @Schema(description = "Indicates if the operation was successful", nullable = false, example = "true")
    private final boolean isSuccess;
    @Schema(description = "Response code", nullable = false, example = "COMMON_200_001")
    private final String code;
    @Schema(description = "Response message", nullable = false, example = "OK")
    private final String message;

}
