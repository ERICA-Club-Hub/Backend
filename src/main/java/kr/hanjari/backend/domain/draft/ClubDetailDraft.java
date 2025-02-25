package kr.hanjari.backend.domain.draft;

import com.fasterxml.jackson.databind.ser.Serializers.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hanjari.backend.domain.common.BaseEntity;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;
import kr.hanjari.backend.web.dto.club.request.ClubDetailRequestDTO;
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
public class ClubDetailDraft  {

    @Id
    @Column(name = "club_id")
    private Long clubId;

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

    public void updateClubDetails(ClubDetailRequestDTO request) {
        this.recruitmentStatus = request.recruitmentStatus();
        this.leaderName = request.leaderName();
        this.leaderPhone = request.leaderPhone();
        this.activities = request.activities();
        this.membershipFee = request.membershipFee();
        this.snsUrl = request.snsUrl();
        this.applicationUrl = request.applicationUrl();
    }
}
