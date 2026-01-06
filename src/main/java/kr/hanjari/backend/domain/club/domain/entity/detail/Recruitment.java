package kr.hanjari.backend.domain.club.domain.entity.detail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.club.presentation.dto.request.ClubRecruitmentRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@Table(name = "recruitment")
@NoArgsConstructor
@AllArgsConstructor
public class Recruitment {

    @Id
    @Column(name = "club_id", nullable = false)
    private Long clubId;

    @MapsId("clubId")
    @OneToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(name = "content1", length = 600)
    private String due;

    @Column(name = "content2", length = 600)
    private String target;

    @Column(name = "content3", length = 600)
    private String notice;

    @Column(name = "content4", length = 600)
    private String etc;

    public void updateRecruitment(ClubRecruitmentRequest clubRecruitmentDTO) {
        this.due = clubRecruitmentDTO.due();
        this.target = clubRecruitmentDTO.target();
        this.notice = clubRecruitmentDTO.notice();
        this.etc = clubRecruitmentDTO.etc();
    }
}
