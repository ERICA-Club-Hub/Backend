package kr.hanjari.backend.domain;

import jakarta.persistence.*;
import kr.hanjari.backend.domain.common.BaseEntity;
import kr.hanjari.backend.domain.enums.ClubCategory;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubDetailDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "club")
@NoArgsConstructor
public class Club extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_file_id")
    private File imageFile;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ClubCategory category;

    @Column(name = "leader_name", nullable = false)
    private String leaderName;

    @Column(name = "leader_email", nullable = false)
    private String leaderEmail;

    @Column(name = "leader_phone", nullable = false)
    private String leaderPhone;

    @Column(name = "one_liner", nullable = false)
    private String oneLiner;

    @Column(name = "brief_introduction", nullable = false)
    private String briefIntroduction;

    @Column(name = "meeting_schedule", nullable = false)
    private String meetingSchedule;

    @Column(name = "membership_fee", nullable = false)
    private Integer membershipFee;

    @Column(name = "sns_url")
    private String snsUrl;

    @Column(name = "application_url")
    private String applicationUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "recruitment_status", nullable = false)
    private RecruitmentStatus recruitmentStatus;

    public void updateClubImage(File imageFile) {
        this.imageFile = imageFile;
    }

    public void updateClubDetails(ClubDetailDTO detail) {
        this.recruitmentStatus = detail.getRecruitmentStatus();
        this.leaderName = detail.getLeaderName();
        this.leaderEmail = detail.getLeaderEmail();
        this.leaderPhone = detail.getLeaderPhone();
        this.meetingSchedule = detail.getActivities();
        this.snsUrl = detail.getSnsUrl();
        this.applicationUrl = detail.getApplicationUrl();
    }

    public void updateCode(String code) {
        this.code = code;
    }


}
