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

    @Column(name = "month")
    private Integer month;

    @Column(name = "content")
    private String content;

    public void updateActivity(ClubActivityDTO clubActivityDTO) {
        this.month = clubActivityDTO.getMonth();
        this.content = clubActivityDTO.getActivity();
    }
}
