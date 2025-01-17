package kr.hanjari.backend.domain.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ActivityImageId implements Serializable {

    @Column(name = "activity_id", nullable = false)
    private Long activityId;

    @Column(name = "image_file_id", nullable = false)
    private Long imageFileId;
}
