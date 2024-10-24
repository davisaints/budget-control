package budget.control.project.dto.response;

import budget.control.project.model.Revenue;
import budget.control.project.utils.BigDecimalUtil;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.time.LocalDate;

@JsonPropertyOrder({"id", "category", "description", "amount", "transactionDate"})
public class RevenueDTOResponse {

  private BigDecimal amount;

  private String description;

  private Long id;

  private LocalDate transactionDate;

  public RevenueDTOResponse(Revenue revenue) {
    this.amount = BigDecimalUtil.roundWithCeiling(revenue.getAmount());
    this.description = revenue.getDescription();
    this.id = revenue.getId();

    this.transactionDate = revenue.getTransactionDate();
  }

  public BigDecimal getAmount() {
    return amount;
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
