package budget.control.project.dto;

import budget.control.project.model.Revenue;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.time.LocalDate;

@JsonPropertyOrder({"category", "description", "amount", "transactionDate"})
public class RevenueDTOResponse {

  Long id;

  BigDecimal amount;

  String description;

  LocalDate transactionDate;

  public RevenueDTOResponse(Revenue revenue) {
    this.amount = revenue.getAmount();
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
