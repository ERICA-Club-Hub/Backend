package kr.hanjari.backend.domain.faq.presentation.controller;

import kr.hanjari.backend.domain.faq.application.service.FaqService;
import kr.hanjari.backend.domain.faq.presentation.dto.request.FaqRequest;
import kr.hanjari.backend.domain.faq.presentation.dto.response.FaqCommandResponse;
import kr.hanjari.backend.domain.faq.presentation.dto.response.GetAllFaqResponse;
import kr.hanjari.backend.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/faq")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    @PostMapping("")
    public ApiResponse<FaqCommandResponse> createFaq(@RequestBody FaqRequest request) {

        FaqCommandResponse result = faqService.createFaq(request);

        return ApiResponse.onSuccess(result);
    }

    @GetMapping("")
    public ApiResponse<GetAllFaqResponse> getAllFaq(@RequestParam(defaultValue = "0") Integer page,
                                                    @RequestParam(defaultValue = "10") Integer size) {

        GetAllFaqResponse result = faqService.getAllFaq(0, 10);

        return ApiResponse.onSuccess(result);
    }
}
