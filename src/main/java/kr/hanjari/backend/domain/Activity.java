package kr.hanjari.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "activity")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @Setter
    private Club club;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "content")
    private String content;

}
