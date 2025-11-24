package kr.hanjari.backend.domain.club.presentation.controller;

import kr.hanjari.backend.domain.club.application.query.ClubQueryService;
import kr.hanjari.backend.domain.club.domain.enums.ClubType;
import kr.hanjari.backend.domain.club.presentation.dto.response.GetInstagrams;
import kr.hanjari.backend.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ClubInstagramSearchController {

    private final ClubQueryService clubQueryService;

    @GetMapping("/instagram")
    public ApiResponse<GetInstagrams> getInstagram(
            @RequestParam ClubType clubType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int size
    ) {
        GetInstagrams result = clubQueryService.findInstagramsByCategory(clubType, page, size);

        return ApiResponse.onSuccess(result);
    }

    @GetMapping("/instagram/popular")
    public ApiResponse<GetInstagrams> getInstagramPopular() {
        GetInstagrams result = clubQueryService.findInstagramsByRandom();

        return ApiResponse.onSuccess(result);
    }



}
