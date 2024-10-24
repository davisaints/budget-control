package budget.control.project.repository;

import budget.control.project.dto.response.CategoryExpenseDTOResponse;
import budget.control.project.model.Expense;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  @Query(
      "SELECT e FROM Expense e "
          + "WHERE EXTRACT(YEAR FROM e.transactionDate) = :year "
          + "AND EXTRACT(MONTH FROM e.transactionDate) = :month")
  Page<Expense> findByYearAndMonth(
      @Param("year") Integer year, @Param("month") Integer month, Pageable pageable);

  @Query(
      "SELECT e FROM Expense e WHERE LOWER(e.description) = LOWER(:description) "
          + "AND e.transactionDate = :transactionDate")
  Expense findByDescriptionAndTransactionDate(
      @Param("description") String description,
      @Param("transactionDate") LocalDate transactionDate);

  @Query(
      "SELECT e FROM Expense e WHERE LOWER(e.description) LIKE LOWER(CONCAT('%', :description,"
          + " '%'))")
  Page<Expense> findByDescriptionContaining(
      @Param("description") String description, Pageable pageable);

  @Query(
      "SELECT COALESCE(SUM(e.amount), 0) "
          + "FROM Expense e "
          + "WHERE EXTRACT(YEAR FROM e.transactionDate) = :year "
          + "AND EXTRACT(MONTH FROM e.transactionDate) = :month")
  BigDecimal findTotalMonthlyExpense(@Param("year") Integer year, @Param("month") Integer month);

  @Query(
      "SELECT new budget.control.project.dto.response.CategoryExpenseDTOResponse(c.name, "
          + "SUM(e.amount)) "
          + "FROM Expense e JOIN e.category c "
          + "WHERE EXTRACT(YEAR FROM e.transactionDate) = :year "
          + "AND EXTRACT(MONTH FROM e.transactionDate) = :month "
          + "GROUP BY c.name "
          + "ORDER BY SUM(e.amount) DESC")
  List<CategoryExpenseDTOResponse> findTotalExpensesPerCategory(
      @Param("year") Integer year, @Param("month") Integer month);
}
