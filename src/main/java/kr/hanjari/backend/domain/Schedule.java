package kr.hanjari.backend.domain;

import jakarta.persistence.*;
import kr.hanjari.backend.domain.key.ScheduleId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@Table(name = "schedule")
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @EmbeddedId
    private ScheduleId id;

    @MapsId("clubId")
    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(name = "content")
    private String content;

}
