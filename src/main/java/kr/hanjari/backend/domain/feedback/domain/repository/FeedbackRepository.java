package kr.hanjari.backend.domain.feedback.domain.repository;

import kr.hanjari.backend.domain.feedback.domain.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
