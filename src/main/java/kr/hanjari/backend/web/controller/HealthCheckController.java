package kr.hanjari.backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.payload.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    @Tag(name = "서버 상태 확인")
    @GetMapping("")
    public ApiResponse<String> healthCheck() {
        return ApiResponse.onSuccess("서버 켜져있어용");
    }
}
