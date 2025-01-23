package kr.hanjari.backend.domain;

import jakarta.persistence.*;
import kr.hanjari.backend.web.dto.club.ClubRequestDTO.ClubActivityDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@Table(name = "activity")
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "content")
    private String content;

}
