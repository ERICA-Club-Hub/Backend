package kr.hanjari.backend.repository.draft;

import kr.hanjari.backend.domain.draft.IntroductionDraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntroductionDraftRepository extends JpaRepository<IntroductionDraft, Long> {
}
