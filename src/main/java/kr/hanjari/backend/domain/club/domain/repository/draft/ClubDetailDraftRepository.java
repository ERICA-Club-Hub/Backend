package kr.hanjari.backend.domain.club.domain.repository.draft;

import kr.hanjari.backend.domain.club.domain.entity.draft.ClubDetailDraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubDetailDraftRepository extends JpaRepository<ClubDetailDraft, Long> {
}
