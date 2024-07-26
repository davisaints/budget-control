package budget.control.project.repository;

import budget.control.project.model.Revenue;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {

  @Query(
      "SELECT r FROM Revenue r WHERE YEAR(r.transactionDate) = :year AND MONTH(r.transactionDate) = :month")
  Page<Revenue> findByYearAndMonth(
      @Param("year") Integer year, @Param("month") Integer month, Pageable pageable);

  Revenue findByDescriptionAndTransactionDate(String description, LocalDate transactionDate);

  Page<Revenue> findByDescriptionContaining(String description, Pageable pageable);
}
