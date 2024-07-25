package budget.control.project.dto;

import budget.control.project.model.Category;
import budget.control.project.utils.BigDecimalUtil;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;

public class ExpenseDTORequest {

  @DecimalMin(value = "0.50")
  BigDecimal amount;

  Category category;

  String categoryName;

  @NotEmpty
  @Length(min = 2)
  String description;

  LocalDate transactionDate;

  public ExpenseDTORequest(
      BigDecimal amount, String categoryName, String description, LocalDate transactionDate) {
    this.amount = BigDecimalUtil.roundWithCeiling(amount);
    this.categoryName = categoryName;
    this.description = description;
    this.transactionDate = transactionDate;
  }

  public String getDescription() {
    return description;
  }

  public BigDecimal getAmount() {
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
