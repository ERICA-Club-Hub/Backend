package kr.hanjari.backend.domain;

import jakarta.persistence.*;
import kr.hanjari.backend.domain.common.BaseEntity;
import kr.hanjari.backend.domain.enums.ClubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "club_registration")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ClubRegistration extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_file_id")
    private File imageFile;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ClubCategory category;

    @Column(name = "leader_email", nullable = false, length = 40)
    private String leaderEmail;

    @Column(name = "one_liner", nullable = false, length = 40)
    private String oneLiner;

    @Column(name = "brief_introduction", nullable = false, length = 120)
    private String briefIntroduction;

    public void updateImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public Club toClub() {
        return Club.builder()
                .imageFile(imageFile)
                .name(name)
                .category(category)
                .leaderEmail(leaderEmail)
                .oneLiner(oneLiner)
                .briefIntroduction(briefIntroduction)
                .build();
    }
}
