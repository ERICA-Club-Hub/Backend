package kr.hanjari.backend.domain.club.presentation.controller;

import kr.hanjari.backend.domain.club.application.query.ClubQueryService;
import kr.hanjari.backend.domain.club.domain.enums.*;
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
            @RequestParam(defaultValue = "10") int size
    ) {
        GetInstagrams result = clubQueryService.findInstagramsByCategory(clubType, page, size);

        return ApiResponse.onSuccess(result);
    }

    @GetMapping("/instagram/central")
    public ApiResponse<GetInstagrams> getInstagramCentral(
            @RequestParam(required = false) CentralClubCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        GetInstagrams result = clubQueryService.findInstagramsCentral(category, page, size);

        return ApiResponse.onSuccess(result);
    }

    @GetMapping("/instagram/union")
    public ApiResponse<GetInstagrams> getInstagramUnion(
            @RequestParam(required = false) UnionClubCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        GetInstagrams result = clubQueryService.findInstagramsUnion(category, page, size);

        return ApiResponse.onSuccess(result);
    }

    @GetMapping("/instagram/college")
    public ApiResponse<GetInstagrams> getInstagramCollege(
            @RequestParam(required = false) College college,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        GetInstagrams result = clubQueryService.findInstagramsCollege(college, page, size);

        return ApiResponse.onSuccess(result);
    }

    @GetMapping("/instagram/department")
    public ApiResponse<GetInstagrams> getInstagramDepartment(
            @RequestParam(required = false) Department department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        GetInstagrams result = clubQueryService.findInstagramsDepartment(department, page, size);

        return ApiResponse.onSuccess(result);
    }

    @GetMapping("/instagram/popular")
    public ApiResponse<GetInstagrams> getInstagramPopular() {
        GetInstagrams result = clubQueryService.findInstagramsByRandom();

        return ApiResponse.onSuccess(result);
    }



}
