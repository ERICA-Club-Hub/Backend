package kr.hanjari.backend.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "announcement")
@NoArgsConstructor
public class Announcement {

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
}
