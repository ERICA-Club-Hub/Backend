package kr.hanjari.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kr.hanjari.backend.domain.command.CategoryCommand;
import kr.hanjari.backend.domain.common.BaseEntity;
import kr.hanjari.backend.domain.vo.ClubCategoryInfo;
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

    @Column(name = "leader_email", nullable = false, length = 40)
    private String leaderEmail;

    @Column(name = "one_liner", nullable = false, length = 40)
    private String oneLiner;

    @Column(name = "brief_introduction", nullable = false, length = 120)
    private String briefIntroduction;

    @Embedded
    private ClubCategoryInfo categoryInfo;

    // 팩토리 메서드
    public static ClubRegistration create(
            String name,
            String leaderEmail,
            CategoryCommand categoryCommand,
            String oneLiner,
            String briefIntroduction
    ) {

        return ClubRegistration.builder()
                .name(name)
                .leaderEmail(leaderEmail)
                .oneLiner(oneLiner)
                .briefIntroduction(briefIntroduction)
                .categoryInfo(ClubCategoryInfo.from(categoryCommand))
                .build();
    }

    // 팩토리/업데이트 시 불변성 체크를 위한 메서드

    public void updateImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

}
