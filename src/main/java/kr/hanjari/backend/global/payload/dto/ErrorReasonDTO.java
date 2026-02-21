package kr.hanjari.backend.global.payload.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@Schema(description = "DTO for error reason")
public class ErrorReasonDTO {

    @Schema(description = "HTTP status code", nullable = false, example = "400")
    private HttpStatus httpStatus;
    @Schema(description = "Indicates if the operation was successful", nullable = false, example = "false")
    private final boolean isSuccess;
    @Schema(description = "Error code", nullable = false, example = "COMMON_400_001")
    private final String code;
    @Schema(description = "Error message", nullable = false, example = "Bad Request")
    private final String message;

}
