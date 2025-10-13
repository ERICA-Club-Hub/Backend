package kr.hanjari.backend.domain.club.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kr.hanjari.backend.domain.club.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubBasicInformationRequest;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubDetailRequest;
import kr.hanjari.backend.domain.command.CategoryCommand;
import kr.hanjari.backend.domain.common.BaseEntity;
import kr.hanjari.backend.domain.file.domain.entity.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "club")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Club extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_file_id")
    private File imageFile;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "leader_name", length = 30)
    private String leaderName;

    @Column(name = "leader_email", nullable = false, length = 40)
    private String leaderEmail;

    @Column(name = "leader_phone")
    private String leaderPhone;

    @Column(name = "one_liner", nullable = false, length = 40)
    private String oneLiner;

    @Column(name = "brief_introduction", nullable = false, length = 120)
    private String briefIntroduction;

    @Column(name = "meeting_schedule", length = 30)
    private String meetingSchedule;

    @Column(name = "membership_fee")
    private Integer membershipFee;

    @Column(name = "sns_url", length = 30)
    private String snsUrl;

    @Column(name = "application_url")
    private String applicationUrl;

    @Column(name = "view_count", nullable = false)
    @Builder.Default
    private Long viewCount = 0L;

    @Enumerated(EnumType.STRING)
    @Column(name = "recruitment_status", nullable = false)
    private RecruitmentStatus recruitmentStatus;

    @Embedded
    private ClubCategoryInfo categoryInfo;

    // 팩토리 메서드
    public static Club create(ClubRegistration clubRegistration) {
        Club club = Club.builder()
                .name(clubRegistration.getName())
                .leaderEmail(clubRegistration.getLeaderEmail())
                .oneLiner(clubRegistration.getOneLiner())
                .briefIntroduction(clubRegistration.getBriefIntroduction())
                .categoryInfo(clubRegistration.getCategoryInfo())
                .imageFile(clubRegistration.getImageFile())
                .build();

        club.updateRecruitmentStatus(0);
        return club;
    }

    public void updateClubImage(File imageFile) {
        this.imageFile = imageFile;
    }

    public void updateClubDetails(ClubDetailRequest detail) {
        this.recruitmentStatus = detail.recruitmentStatus();
        this.leaderName = detail.leaderName();
        this.leaderPhone = detail.leaderPhone();
        this.membershipFee = detail.membershipFee();
        this.meetingSchedule = detail.activities();
        this.snsUrl = detail.snsUrl();
        this.applicationUrl = detail.applicationUrl();
    }

    public void updateClubCommonInfo(ClubBasicInformationRequest commonInfo, CategoryCommand categoryCommand) {
        this.name = commonInfo.clubName();
        this.leaderEmail = commonInfo.leaderEmail();
        this.oneLiner = commonInfo.oneLiner();
        this.briefIntroduction = commonInfo.briefIntroduction();
        this.categoryInfo.apply(categoryCommand);
    }

    public void updateCode(String code) {
        this.code = code;
    }

    public void updateRecruitmentStatus(int option) {
        switch (option) {
            case 0 -> this.recruitmentStatus = RecruitmentStatus.UPCOMING;
            case 1 -> this.recruitmentStatus = RecruitmentStatus.OPEN;
            case 2 -> this.recruitmentStatus = RecruitmentStatus.CLOSED;
        }
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

}
