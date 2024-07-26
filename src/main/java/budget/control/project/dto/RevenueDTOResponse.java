package budget.control.project.dto;

import budget.control.project.model.Revenue;
import budget.control.project.utils.BigDecimalUtil;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.time.LocalDate;

@JsonPropertyOrder({"category", "description", "amount", "transactionDate"})
public class RevenueDTOResponse {

  private BigDecimal amount;

  private String description;

  private LocalDate transactionDate;

  public RevenueDTOResponse(Revenue revenue) {
    this.amount = BigDecimalUtil.roundWithCeiling(revenue.getAmount());
    this.description = revenue.getDescription();
    this.transactionDate = revenue.getTransactionDate();
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
