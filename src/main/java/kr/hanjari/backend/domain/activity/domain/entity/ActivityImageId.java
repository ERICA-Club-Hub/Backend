package kr.hanjari.backend.domain.activity.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
public class ActivityImageId implements Serializable {

    public ActivityImageId(Long activityId, Long fileId) {
        this.activityId = activityId;
        this.imageFileId = fileId;
    }

    @Column(name = "activity_id", nullable = false)
    private Long activityId;

    @Column(name = "image_file_id", nullable = false)
    private Long imageFileId;

}
