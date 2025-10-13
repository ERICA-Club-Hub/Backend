package kr.hanjari.backend.domain.club.domain.entity.draft;

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
@Table(name = "recruitment_draft")
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentDraft {

    @Id
    @Column(name = "club_id", nullable = false)
    private Long clubId;

    @MapsId("clubId")
    @OneToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(name = "content1")
    private String content1;

    @Column(name = "content2")
    private String content2;

    @Column(name = "content3")
    private String content3;

    public void updateRecruitment(ClubRecruitmentRequest clubRecruitmentDTO) {
        this.content1 = clubRecruitmentDTO.due();
        this.content2 = clubRecruitmentDTO.notice();
        this.content3 = clubRecruitmentDTO.etc();
    }

}
