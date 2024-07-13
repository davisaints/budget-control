package budget.control.project.dto;

import budget.control.project.model.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;

public class ExpenseDTORequest {

  @DecimalMin(value = "0.50")
  double amount;

  Category category;

  String categoryName;

  @NotEmpty
  @Length(min = 2)
  String description;

  @PastOrPresent LocalDate transactionDate;

  public ExpenseDTORequest(
      double amount, String categoryName, String description, LocalDate transactionDate) {
    this.amount = amount;
    this.categoryName = categoryName;
    this.description = description;
    this.transactionDate = transactionDate;
  }

  public String getDescription() {
    return description;
  }

  public double getAmount() {
    return amount;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public LocalDate getTransactionDate() {
    return transactionDate;
  }
}
