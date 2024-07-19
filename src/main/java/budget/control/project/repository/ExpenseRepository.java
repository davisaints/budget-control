package budget.control.project.repository;

import budget.control.project.model.Expense;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  @Query(
      "select e from Expense e where year(e.transactionDate) = :year and month(e.transactionDate) = :month")
  Page<Expense> findByYearAndMonth(
      @Param("year") Integer year, @Param("month") Integer month, Pageable pageable);

  Expense findByDescriptionAndTransactionDate(String description, LocalDate transactionDate);

  Page<Expense> findByDescriptionContaining(String description, Pageable pageable);
}
