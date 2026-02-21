package kr.hanjari.backend.domain.feedback.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.domain.feedback.application.service.FeedbackService;
import kr.hanjari.backend.domain.feedback.presentation.dto.FeedbackDTO;
import kr.hanjari.backend.domain.feedback.presentation.dto.request.FeedbackRequest;
import kr.hanjari.backend.domain.feedback.presentation.dto.response.FeedbackCommandResponse;
import kr.hanjari.backend.domain.feedback.presentation.dto.response.GetFeedbacksResponse;
import kr.hanjari.backend.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<FeedbackCommandResponse> createFeedback(@RequestBody FeedbackRequest requestBody) {

        FeedbackCommandResponse result = feedbackService.createFeedback(requestBody);
        return ApiResponse.onSuccess(result);
    }


    @GetMapping("")
    public ApiResponse<GetFeedbacksResponse> getAllFeedbacks(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size
    ) {

        GetFeedbacksResponse result = feedbackService.getFeedbacks(page, size);
        return ApiResponse.onSuccess(result);
    }
}
