package kr.hanjari.backend.domain.activity.domain.entity;

import jakarta.persistence.*;
import kr.hanjari.backend.domain.file.domain.entity.File;
import lombok.*;

@Entity
@Table(name = "activity_image")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ActivityImage {

    @EmbeddedId
    private ActivityImageId id;

    @MapsId("activityId")
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @MapsId("imageFileId")
    @OneToOne
    @JoinColumn(name = "image_file_id")
    private File imageFile;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

}
