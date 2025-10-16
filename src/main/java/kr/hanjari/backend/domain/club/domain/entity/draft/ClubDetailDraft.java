package kr.hanjari.backend.domain.club.domain.entity.draft;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hanjari.backend.domain.club.enums.RecruitmentStatus;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubDetailRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "one_liner")
    String oneLiner;

    @Enumerated(EnumType.STRING)
    RecruitmentStatus recruitmentStatus;

    @Column(name = "leader_name")
    String leaderName;

    @Column(name = "leader_phone")
    String leaderPhone;

    @Column(name = "activities")
    String activities;

    @Column(name = "membership_fee")
    Integer membershipFee;

    @Column(name = "sns_url")
    String snsUrl;

    @Column(name = "application_url")
    String applicationUrl;

    public void updateClubDetails(ClubDetailRequest request) {
        this.leaderName = request.leaderName();
        this.leaderPhone = request.leaderPhone();
        this.activities = request.activities();
        this.membershipFee = request.membershipFee();
        this.snsUrl = request.snsUrl();
        this.applicationUrl = request.applicationUrl();
    }
}
