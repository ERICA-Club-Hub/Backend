package kr.hanjari.backend.domain.club.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hanjari.backend.domain.club.application.query.impl.ClubViewCountTestService;
import kr.hanjari.backend.domain.club.presentation.dto.response.ClubDetailResponse;
import kr.hanjari.backend.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile({"local", "dev"})
@RestController
@RequestMapping("/api/test/clubs")
@RequiredArgsConstructor
@Tag(name = "Club ViewCount Test", description = "조회수 동시성 전략별 테스트 API")
public class ClubViewCountTestController {

    private final ClubViewCountTestService clubViewCountTestService;

    @Operation(summary = "[테스트] Dirty Check")
    @GetMapping("/{clubId}/dirty-check")
    public ApiResponse<ClubDetailResponse> dirtyCheck(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubViewCountTestService.findClubDetailWithDirtyCheck(clubId));
    }

    @Operation(summary = "[테스트] Pessimistic Lock")
    @GetMapping("/{clubId}/pessimistic-lock")
    public ApiResponse<ClubDetailResponse> pessimisticLock(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubViewCountTestService.findClubDetailWithPessimisticLock(clubId));
    }

//    @Operation(summary = "[테스트] Optimistic Lock")
//    @GetMapping("/{clubId}/optimistic-lock")
//    public ApiResponse<ClubDetailResponse> optimisticLock(@PathVariable Long clubId) {
//        return ApiResponse.onSuccess(clubViewCountTestService.findClubDetailWithOptimisticLock(clubId));
//    }

    @Operation(summary = "[테스트] Atomic Operation (@Modifying)")
    @GetMapping("/{clubId}/atomic")
    public ApiResponse<ClubDetailResponse> atomicOperation(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubViewCountTestService.findClubDetailWithAtomicOperation(clubId));
    }

    @Operation(summary = "[테스트] Redis")
    @GetMapping("/{clubId}/redis")
    public ApiResponse<ClubDetailResponse> redis(@PathVariable Long clubId) {
        return ApiResponse.onSuccess(clubViewCountTestService.findClubDetailWithRedis(clubId));
    }
}