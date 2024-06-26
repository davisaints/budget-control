package budget.control.project.repository;

import budget.control.project.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Expense findByDescriptionAndDate(String description, LocalDate date);

}
