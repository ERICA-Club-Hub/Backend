package kr.hanjari.backend.domain.draft;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.hanjari.backend.domain.Club;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@Table(name = "schedule_draft")
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDraft {

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
