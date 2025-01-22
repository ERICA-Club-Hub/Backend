package kr.hanjari.backend.web.dto.club;

import java.util.List;
import java.util.stream.Collectors;
import kr.hanjari.backend.domain.Activity;
import kr.hanjari.backend.domain.Introduction;
import kr.hanjari.backend.domain.Recruitment;
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
        private Integer totalElements;

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        private static class ActivityDTO {
            private Long id;
            private Integer month;
            private String content;
        }


        public static ClubActivityDTO of(List<Activity> activities) {
            return ClubActivityDTO.builder()
                    .activities(activities.stream()
                            .map(activity -> ActivityDTO.builder()
                                    .id(activity.getId())
                                    .month(activity.getMonth())
                                    .content(activity.getContent())
                                    .build())
                            .collect(Collectors.toList()))
                    .totalElements(activities.size())
                    .build();
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

        public static ClubRecruitmentDTO of(Recruitment recruitment) {
            return ClubRecruitmentDTO.builder()
                    .club(ClubDTO.builder()
                            .id(recruitment.getClub().getId())
                            .name(recruitment.getClub().getName())
                            .description(recruitment.getClub().getBriefIntroduction())
                            .category(recruitment.getClub().getCategory())
                            .recruitmentStatus(recruitment.getClub().getRecruitmentStatus())
                            .build())
                    .due(recruitment.getContent1())
                    .notice(recruitment.getContent2())
                    .etc(recruitment.getContent3())
                    .build();
        }
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

        public static ClubIntroductionDTO of(Introduction introduction){
            return ClubIntroductionDTO.builder()
                    .club(ClubDTO.builder()
                            .id(introduction.getClub().getId())
                            .name(introduction.getClub().getName())
                            .description(introduction.getClub().getBriefIntroduction())
                            .category(introduction.getClub().getCategory())
                            .recruitmentStatus(introduction.getClub().getRecruitmentStatus())
                            .build())
                    .introduction(introduction.getContent1())
                    .activity(introduction.getContent2())
                    .recruitment(introduction.getContent3())
                    .build();
        }
    }

}
