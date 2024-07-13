package budget.control.project.repository;

import budget.control.project.model.Revenue;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {

  Revenue findByDescriptionAndDate(String description, LocalDate date);

  Page<Revenue> findByDescriptionContaining(String description, Pageable pageable);
}
