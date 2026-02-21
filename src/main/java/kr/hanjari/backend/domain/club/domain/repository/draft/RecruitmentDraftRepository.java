package kr.hanjari.backend.domain.club.domain.repository.draft;

import java.util.Optional;
import kr.hanjari.backend.domain.club.domain.entity.draft.RecruitmentDraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentDraftRepository extends JpaRepository<RecruitmentDraft, Long> {
    Optional<RecruitmentDraft> findByClubId(Long clubId);
    boolean existsByClubId(Long clubId);
    void removeByClubId(Long clubId);
}
