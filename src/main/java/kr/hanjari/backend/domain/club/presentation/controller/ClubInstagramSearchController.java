package kr.hanjari.backend.domain.club.presentation.controller;

import kr.hanjari.backend.domain.club.application.query.ClubQueryService;
import kr.hanjari.backend.domain.club.domain.enums.ClubType;
import kr.hanjari.backend.domain.club.presentation.dto.response.GetOfficialAccounts;
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

    @GetMapping("/instagram/central")
    public ApiResponse<GetOfficialAccounts> getInstagramCentral(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int size
    ) {
        GetOfficialAccounts result = clubQueryService.findInstagramsByCategory(ClubType.CENTRAL, page, size);

        return ApiResponse.onSuccess(result);
    }

    @GetMapping("/instagram/college")
    public ApiResponse<GetOfficialAccounts> getInstagramCollege(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int size
    ) {
        GetOfficialAccounts result = clubQueryService.findInstagramsByCategory(ClubType.CENTRAL, page, size);

        return ApiResponse.onSuccess(result);
    }

    @GetMapping("/instagram/department")
    public ApiResponse<GetOfficialAccounts> getInstagramDepartment(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int size
    ) {
        GetOfficialAccounts result = clubQueryService.findInstagramsByCategory(ClubType.CENTRAL, page, size);

        return ApiResponse.onSuccess(result);
    }

    @GetMapping("/instagram/union")
    public ApiResponse<GetOfficialAccounts> getInstagramUnion(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int size
    ) {
        GetOfficialAccounts result = clubQueryService.findInstagramsByCategory(ClubType.CENTRAL, page, size);

        return ApiResponse.onSuccess(result);
    }

    @GetMapping("/instagram/popular")
    public ApiResponse<GetOfficialAccounts> getInstagramPopular() {
        GetOfficialAccounts result = clubQueryService.findInstagramsByRandom();

        return ApiResponse.onSuccess(result);
    }



}
