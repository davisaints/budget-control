package budget.control.project.repository;

import budget.control.project.model.Expense;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  Expense findByDescriptionAndTransactionDate(String description, LocalDate transactionDate);

  Page<Expense> findDescriptionContaining(String description, Pageable pageable);
}
