package kr.hanjari.backend.domain.tag.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kr.hanjari.backend.domain.club.domain.entity.Club;
import kr.hanjari.backend.domain.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "club_tag",
        uniqueConstraints = @UniqueConstraint(columnNames = {"club_id", "tag_id"}))
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    public static ClubTag create(Club club, Tag tag) {
        return ClubTag.builder()
                .club(club)
                .tag(tag)
                .build();
    }
}
