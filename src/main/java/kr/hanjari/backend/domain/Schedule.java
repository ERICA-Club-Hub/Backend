package kr.hanjari.backend.domain;

import jakarta.persistence.*;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(name = "month", nullable = false)
    private Integer month;

    @Column(name = "content")
    private String content;

    public void updateSchedule(Integer month, String content) {
        this.month = month;
        this.content = content;
    }

}
