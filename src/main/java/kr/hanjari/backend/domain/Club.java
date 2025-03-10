package kr.hanjari.backend.domain;

import jakarta.persistence.*;
import kr.hanjari.backend.domain.common.BaseEntity;
import kr.hanjari.backend.domain.enums.ClubCategory;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.web.dto.club.request.ClubDetailRequestDTO;
import kr.hanjari.backend.web.dto.club.request.ClubBasicInformationDTO;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ClubCategory category;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "recruitment_status", nullable = false)
    private RecruitmentStatus recruitmentStatus;

    public void updateClubImage(File imageFile) {
        this.imageFile = imageFile;
    }

    public void updateClubDetails(ClubDetailRequestDTO detail) {
        this.recruitmentStatus = detail.recruitmentStatus();
        this.leaderName = detail.leaderName();
        this.leaderPhone = detail.leaderPhone();
        this.membershipFee = detail. membershipFee();
        this.meetingSchedule = detail.activities();
        this.snsUrl = detail.snsUrl();
        this.applicationUrl = detail.applicationUrl();
    }

    public void updateClubCommonInfo(ClubBasicInformationDTO commonInfo) {
        this.name = commonInfo.clubName();
        this.leaderEmail = commonInfo.leaderEmail();
        this.category = ClubCategory.valueOf(commonInfo.category());
        this.oneLiner = commonInfo.oneLiner();
        this.briefIntroduction = commonInfo.briefIntroduction();
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

}
