package kr.hanjari.backend.domain.club.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(
        name = "recruitment_alert_subscription",
        uniqueConstraints = @UniqueConstraint(columnNames = {"club_id", "email"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentAlertSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Builder
    private RecruitmentAlertSubscription(Club club, String email) {
        this.club = club;
        this.email = email;
    }

    public static RecruitmentAlertSubscription create(Club club, String email) {
        return RecruitmentAlertSubscription.builder()
                .club(club)
                .email(email)
                .build();
    }
}
