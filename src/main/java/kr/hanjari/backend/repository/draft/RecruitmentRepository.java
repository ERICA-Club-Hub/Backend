package kr.hanjari.backend.repository.draft;

import kr.hanjari.backend.domain.draft.RecruitmentDraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRepository extends JpaRepository<RecruitmentDraft, Long> {
}
