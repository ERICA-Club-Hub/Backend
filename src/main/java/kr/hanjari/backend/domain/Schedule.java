package kr.hanjari.backend.domain;

import jakarta.persistence.*;
import kr.hanjari.backend.domain.key.ScheduleId;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule {

    @EmbeddedId
    private ScheduleId id;

    @MapsId("clubId")
    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

}
