package kr.hanjari.backend.domain.club.domain.entity.draft;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@Table(name = "schedule_description_draft")
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDescriptionDraft {

  @Id
  @Column(name = "club_id", nullable = false)
  private Long clubId;

  @MapsId("clubId")
  @OneToOne
  @JoinColumn(name = "club_id")
  private Club club;

  @Column(name = "description")
  private String description;

  public void updateDescription(String description) {
    this.description = description;
  }
}
