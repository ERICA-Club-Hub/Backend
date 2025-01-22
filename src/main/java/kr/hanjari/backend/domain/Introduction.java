package kr.hanjari.backend.domain;

import jakarta.persistence.*;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubIntroductionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@Table(name = "introduction")
@NoArgsConstructor
@AllArgsConstructor
public class Introduction {

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

    public void updateIntroduction(ClubIntroductionDTO requestDTO) {
        this.content1 = requestDTO.getIntroduction();
        this.content2 = requestDTO.getActivity();
        this.content3 = requestDTO.getRecruitment();
    }

}
