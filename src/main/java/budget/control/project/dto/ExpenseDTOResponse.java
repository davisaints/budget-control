package budget.control.project.dto;

import budget.control.project.model.Category;
import budget.control.project.model.Expense;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;

public class ExpenseDTOResponse {

  private double amount;

  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Category category;

  private LocalDate date;

  private String description;

  public ExpenseDTOResponse(Expense expense) {
    this.amount = expense.getAmount();
    this.category = expense.getCategory();
    this.date = expense.getDate();
    this.description = expense.getDescription();
  }

  public double getAmount() {
    return amount;
  }

  public Category getCategory() {
    return category;
  }

  public LocalDate getDate() {
    return date;
  }

  public String getDescription() {
    return description;
  }
}
