package kr.hanjari.backend.domain.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class ScheduleId implements Serializable {

    @Column(name = "club_id", nullable = false)
    private Long clubId;

    @Column(name = "month", nullable = false)
    private Integer month;
}
