package budget.control.project.dto;

import budget.control.project.model.Category;
import budget.control.project.model.Revenue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;

public class RevenueDTOResponse {

  Long id;

  double amount;

  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  Category category;

  LocalDate date;

  String description;

  public RevenueDTOResponse(Revenue revenue) {
    this.amount = revenue.getAmount();
    this.category = revenue.getCategory();
    this.date = revenue.getDate();
    this.description = revenue.getDescription();
  }

  public double getAmount() {
    return amount;
  }

  public Category getCategory() {
    return category;
  }

  public LocalDate getDate() {
    return date;
  }

  public String getDescription() {
    return description;
  }
}
