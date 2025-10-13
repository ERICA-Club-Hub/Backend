package kr.hanjari.backend.domain.feedback.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.domain.feedback.application.service.FeedbackService;
import kr.hanjari.backend.domain.feedback.presentation.dto.FeedbackDTO;
import kr.hanjari.backend.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "Feedback API")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Operation(summary = "[피드백] 피드백 제출", description = """
            ## 피드백을 제출합니다.
            
            ### Requset
            #### requestBody (JSON)
            - **content**: 내용
            """
    )
    @PostMapping("")
    public ApiResponse<Void> postFeedback(@RequestBody FeedbackDTO requestBody) {

        feedbackService.saveFeedback(requestBody);
        return ApiResponse.onSuccess();
    }
}
