package budget.control.project.repository;

import budget.control.project.model.Revenue;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {

  @Query(
      "SELECT r FROM Revenue r "
          + "WHERE EXTRACT(YEAR FROM r.transactionDate) = :year "
          + "AND EXTRACT(MONTH FROM r.transactionDate) = :month")
  Page<Revenue> findByYearAndMonth(
      @Param("year") Integer year, @Param("month") Integer month, Pageable pageable);

  @Query(
      "SELECT r FROM Revenue r WHERE r.description ILIKE :description "
          + "AND r.transactionDate = :transactionDate")
  Revenue findByDescriptionAndTransactionDate(
      @Param("description") String description,
      @Param("transactionDate") LocalDate transactionDate);

  @Query("SELECT r FROM Revenue r WHERE r.description ILIKE %:description%")
  Page<Revenue> findByDescriptionContaining(
      @Param("description") String description, Pageable pageable);

  @Query(
      "SELECT COALESCE(SUM(r.amount), 0) "
          + "FROM Revenue r "
          + "WHERE EXTRACT(YEAR FROM r.transactionDate) = :year "
          + "AND EXTRACT(MONTH FROM r.transactionDate) = :month")
  BigDecimal findTotalMonthlyRevenue(@Param("year") Integer year, @Param("month") Integer month);
}
