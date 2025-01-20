package kr.hanjari.backend.web.dto.club;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ClubRequestDTO {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClubDetailDTO {
        @Schema(description = "동아리 모집 상태", example = "UPCOMING")
        private RecruitmentStatus recruitmentStatus;
        @Schema(description = "동아리 대표 이름", example = "홍길동")
        private String leaderName;
        @Schema(description = "동아리 대표 전화번호", example = "010-1234-5678")
        private String leaderPhone;
        @Schema(description = "동아리 활동내용", example = "매주 금요일 18시에 모여서 활동합니다.")
        private String activities;
        @Schema(description = "동아리 SNS URL", example = "https://www.instagram.com/hanjari")
        private String snsUrl;
        @Schema(description = "동아리 지원 URL", example = "https://www.naver.com")
        private String applicationUrl;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClubActivityDTO {
        @Schema(description = "활동 월", example = "10")
        @Min(1) @Max(12)
        private Integer month;
        @Schema(description = "활동 내용", example = "스터디 시작")
        private String activity;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClubIntroductionDTO {
        @Schema(description = "동아리 소개", example = "대학생 IT 연합 개발 동아리")
        private String introduction;
        @Schema(description = "활동 내용", example = "챌린지 : ~~~~, 스터디 : ~~~~")
        private String activity;
        @Schema(description = "원하는 동아리 원", example = "스스로 성장할 수 있는 분! ")
        private String recruitment;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClubRecruitmentDTO {
        @Schema(description = "동아리 모집 기간", example = "서류 모집 : 10.1 ~ 10.15, 면접 : 10.16 ~ 10.20")
        private String due;
        @Schema(description = "유의사항", example = "서류 제출은 이메일로 해주세요!")
        private String notice;
        @Schema(description = "기타", example = "기타 문의 사항은 회장 개인 전화번호로 연락 주세요! ")
        private String etc;
    }


}
