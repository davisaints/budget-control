package budget.control.project.dto;

import budget.control.project.utils.BigDecimalUtil;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;

public class RevenueDTORequest {

  @DecimalMin(value = "0.50")
  private BigDecimal amount;

  @NotEmpty
  @Length(min = 2)
  private String description;

  private LocalDate transactionDate;

  public RevenueDTORequest(BigDecimal amount, String description, LocalDate transactionDate) {
    this.description = description;
    this.amount = BigDecimalUtil.roundWithCeiling(amount);
    this.transactionDate = transactionDate;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getTransactionDate() {
    return transactionDate;
  }
}
