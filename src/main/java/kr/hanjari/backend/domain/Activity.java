package kr.hanjari.backend.domain;

import jakarta.persistence.*;
import kr.hanjari.backend.domain.common.BaseEntity;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "activity")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Activity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @Setter
    private Club club;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "content", length = 120)
    private String content;

    public void updateContentAndDate(String content, LocalDate date) {
        this.content = content;
        this.date = date;
    }
}
