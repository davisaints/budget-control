package budget.control.project.dto.response;

import budget.control.project.model.Category;
import budget.control.project.model.Expense;
import budget.control.project.utils.BigDecimalUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.time.LocalDate;

@JsonPropertyOrder({"id", "category", "description", "amount", "transactionDate"})
public class ExpenseDTOResponse {

  private BigDecimal amount;

  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Category category;

  private String description;

  private Long id;

  private LocalDate transactionDate;

  public ExpenseDTOResponse(Expense expense) {
    this.amount = BigDecimalUtil.roundWithCeiling(expense.getAmount());
    this.category = expense.getCategory();
    this.description = expense.getDescription();
    this.id = expense.getId();
    this.transactionDate = expense.getTransactionDate();
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Category getCategory() {
    return category;
  }

  public String getDescription() {
    return description;
  }

  public Long getId() {
    return id;
  }

  public LocalDate getTransactionDate() {
    return transactionDate;
  }
}
