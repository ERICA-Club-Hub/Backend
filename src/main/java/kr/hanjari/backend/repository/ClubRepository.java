package kr.hanjari.backend.repository;

import java.util.Optional;
import kr.hanjari.backend.domain.Club;
import kr.hanjari.backend.domain.enums.CentralClubCategory;
import kr.hanjari.backend.domain.enums.RecruitmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long>, JpaSpecificationExecutor<Club> {

    Optional<Club> findByCode(String code);

    boolean existsByCode(String code);

    @Query("SELECT c FROM Club c WHERE (:name IS NULL OR c.name LIKE %:name%) " +
            "AND (:category IS NULL OR c.categoryInfo.centralCategory = :category) " +
            "AND (:status IS NULL OR c.recruitmentStatus = :status) " +
            "ORDER BY " +
            "CASE WHEN c.recruitmentStatus = 'OPEN' THEN 0 " +
            "     WHEN c.recruitmentStatus = 'UPCOMING' THEN 1 " +
            "     WHEN c.recruitmentStatus = 'CLOSED' THEN 2 " +
            "     ELSE 3 END ASC, c.name ASC")
    Page<Club> findClubsOrderByRecruitmentStatus(String name, CentralClubCategory category, RecruitmentStatus status,
                                                 Pageable pageable);

}
