package budget.control.project.dto;

import budget.control.project.model.Expense;
import java.time.LocalDate;

public class ExpenseDTOResponse {

  double amount;
  LocalDate date;
  String description;

  public ExpenseDTOResponse(Expense expense) {
    this.amount = expense.getAmount();
    this.date = expense.getDate();
    this.description = expense.getDescription();
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
