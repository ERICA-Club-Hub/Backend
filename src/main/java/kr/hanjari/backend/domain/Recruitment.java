package kr.hanjari.backend.domain;

import jakarta.persistence.*;
import kr.hanjari.backend.web.dto.club.request.ClubRecruitmentRequestDTO;
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

    @Column(name = "content1")
    private String content1;

    @Column(name = "content2")
    private String content2;

    @Column(name = "content3")
    private String content3;

    public void updateRecruitment(ClubRecruitmentRequestDTO clubRecruitmentDTO) {
        this.content1 = clubRecruitmentDTO.due();
        this.content2 = clubRecruitmentDTO.notice();
        this.content3 = clubRecruitmentDTO.etc();
    }
}
