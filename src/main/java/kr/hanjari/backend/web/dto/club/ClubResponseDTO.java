package kr.hanjari.backend.web.dto.club;

import java.util.List;
import kr.hanjari.backend.domain.enums.ClubCategory;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ClubResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClubSearchDTO {
        List<ClubDTO> clubs;
        int totalElements;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClubDTO {
        private Long id;
        private String name;
        private String description;
        private ClubCategory category;
        private RecruitmentStatus recruitmentStatus;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClubDetailDTO {
        private ClubDTO club;
        private String profileImageUrl;
        private String leaderName;
        private String leaderEmail;
        private String leaderPhone;
        private String activities;
        private String snsUrl;
        private String applicationUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClubActivityDTO {
        private List<ActivityDTO> activities;
        private Long totalElements;

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        private static class ActivityDTO {
            private Long id;
            private String date;
            private String content;
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClubRecruitmentDTO {
        private ClubDTO club;
        private String due;
        private String notice;
        private String etc;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClubIntroductionDTO {
        private ClubDTO club;
        private String introduction;
        private String activity;
        private String recruitment;
    }

}
