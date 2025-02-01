package kr.hanjari.backend.domain;

import jakarta.persistence.*;
import kr.hanjari.backend.domain.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "announcement")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Announcement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "thumbnail_image", nullable = false)
    private File thumbnailImage;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "url", nullable = false)
    private String url;

    public void updateThumbnailImage(File imageFile) {
        this.thumbnailImage = imageFile;
    }

    public void updateTitleAndUrl(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
