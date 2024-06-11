package budget.control.project.repository;

import budget.control.project.model.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {
    Revenue findByDescriptionAndDate(String description, LocalDate date);
}
