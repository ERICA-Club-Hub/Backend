package kr.hanjari.backend.repository.draft;

import java.util.List;
import kr.hanjari.backend.domain.draft.ScheduleDraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleDraftRepository extends JpaRepository<ScheduleDraft, Long> {
    void deleteByClubId(Long clubId);
    List<ScheduleDraft> findAllByClubIdOrderByMonth(Long clubId);
}
