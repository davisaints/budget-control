package budget.control.project.dto;

import budget.control.project.model.Category;
import budget.control.project.model.Expense;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;

public class ExpenseDTOResponse {

  private double amount;

  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Category category;

  private String description;

  private LocalDate transactionDate;

  public ExpenseDTOResponse(Expense expense) {
    this.amount = expense.getAmount();
    this.category = expense.getCategory();
    this.description = expense.getDescription();
    this.transactionDate = expense.getTransactionDate();
  }

  public double getAmount() {
    return amount;
  }

  public Category getCategory() {
    return category;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getTransactionDate() {
    return transactionDate;
  }
}
