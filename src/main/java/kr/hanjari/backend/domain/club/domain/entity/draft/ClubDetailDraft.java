package kr.hanjari.backend.domain.club.domain.entity.draft;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hanjari.backend.domain.club.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubDetailRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;

@Getter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubDetailDraft {

    @Id
    @Column(name = "club_id")
    private Long clubId;

    @Column(name = "description")
    String description;

    @Enumerated(EnumType.STRING)
    RecruitmentStatus recruitmentStatus;

    @Column(name = "leader_name")
    String leaderName;

    @Column(name = "contact_email")
    String contactEmail;

    @Column(name = "leader_phone")
    String leaderPhone;

    @Column(name = "membership_fee")
    String membershipFee;

    @Column(name = "sns_account")
    String snsAccount;

    @Column(name = "application_url")
    String applicationUrl;

    public void updateClubDetails(ClubDetailRequest request) {
        this.leaderName = request.leaderName();
        this.description = request.description();
        this.leaderPhone = request.leaderPhone();
        this.contactEmail = request.contactEmail();
        this.membershipFee = request.membershipFee();
        this.snsAccount = request.snsAccount();
        this.applicationUrl = request.applicationUrl();
    }
}
