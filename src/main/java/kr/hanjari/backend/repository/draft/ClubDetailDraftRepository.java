package kr.hanjari.backend.repository.draft;

import kr.hanjari.backend.domain.draft.ClubDetailDraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubDetailDraftRepository extends JpaRepository<ClubDetailDraft, Long> {
}
