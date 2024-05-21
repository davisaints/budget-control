package budget.control.project.repository;

import budget.control.project.model.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {
    List<Revenue> findByDescriptionAndDate(String description, LocalDate date);
}
