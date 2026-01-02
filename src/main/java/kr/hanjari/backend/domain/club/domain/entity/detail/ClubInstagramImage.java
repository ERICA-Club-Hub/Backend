package kr.hanjari.backend.domain.club.domain.entity.detail;

import jakarta.persistence.*;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "club_instagram_image")
@Getter
@NoArgsConstructor
public class ClubInstagramImage {

    public ClubInstagramImage(Long clubId) {
        this.clubId = clubId;
    }

    @Id
    @Column(name = "club_id")
    private Long clubId;

    @Setter
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
