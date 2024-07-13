package budget.control.project.dto;

import budget.control.project.model.Category;
import budget.control.project.model.Revenue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDate;

@JsonPropertyOrder({"category", "description", "amount", "transactionDate"})
public class RevenueDTOResponse {

  Long id;

  double amount;

  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  Category category;

  String description;

  LocalDate transactionDate;

  public RevenueDTOResponse(Revenue revenue) {
    this.amount = revenue.getAmount();
    this.category = revenue.getCategory();
    this.description = revenue.getDescription();
    this.transactionDate = revenue.getTransactionDate();
  }

  public double getAmount() {
    return amount;
  }

  public Category getCategory() {
    return category;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getTransactionDate() {
    return transactionDate;
  }
}
