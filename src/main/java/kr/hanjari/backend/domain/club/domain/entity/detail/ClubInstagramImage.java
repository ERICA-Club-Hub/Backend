package kr.hanjari.backend.domain.club.domain.entity.detail;

import jakarta.persistence.*;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "club_instagram_iamge")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClubInstagramImage {

    @Id
    @Column(name = "club_id")
    private Long clubId;

    @MapsId(value = "clubId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(name = "url", length = 1024)
    private String url;

    public void update(String url) {
        this.url = url;
    }

}
