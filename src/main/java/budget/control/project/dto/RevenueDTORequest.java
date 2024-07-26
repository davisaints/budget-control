package budget.control.project.dto;

import budget.control.project.utils.BigDecimalUtil;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class RevenueDTORequest {

  @DecimalMin(value = "0.50")
  private BigDecimal amount;

  @NotBlank(message = "Description is mandatory")
  private String description;

  @NotNull(message = "Transaction date is mandatory")
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
