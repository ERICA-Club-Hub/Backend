package kr.hanjari.backend.domain.club.domain.entity.detail;

import jakarta.persistence.*;
import kr.hanjari.backend.domain.club.domain.entity.Club;
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

    @Column(name = "content1", length = 600)
    private String content1;

    @Column(name = "content2", length = 1200)
    private String content2;

    @Column(name = "content3", length = 600)
    private String content3;

    public void updateIntroduction(String content1, String content2, String content3 ) {
        this.content1 = content1;
        this.content2 = content2;
        this.content3 = content3;
    }

}
