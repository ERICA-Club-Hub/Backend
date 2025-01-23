package kr.hanjari.backend.domain.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleId implements Serializable {

    @Column(name = "club_id", nullable = false)
    private Long clubId;

    @Column(name = "month", nullable = false)
    private Integer month;
}
