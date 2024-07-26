package budget.control.project.dto;

import budget.control.project.model.Category;
import budget.control.project.utils.BigDecimalUtil;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseDTORequest {

  @DecimalMin(value = "0.05")
  private BigDecimal amount;

  private Category category;

  private String categoryName;

  @NotBlank(message = "Description is mandatory")
  private String description;

  @NotNull(message = "Transaction date is mandatory")
  private LocalDate transactionDate;

  public ExpenseDTORequest(
      BigDecimal amount, String categoryName, String description, LocalDate transactionDate) {
    this.amount = BigDecimalUtil.roundWithCeiling(amount);
    this.categoryName = categoryName;
    this.description = description;
    this.transactionDate = transactionDate;
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

  public String getDescription() {
    return description;
  }

  public LocalDate getTransactionDate() {
    return transactionDate;
  }
}
