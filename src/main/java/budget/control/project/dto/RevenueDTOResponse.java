package budget.control.project.dto;

import budget.control.project.model.Revenue;
import java.time.LocalDate;

public class RevenueDTOResponse {

  double amount;
  LocalDate date;
  String description;
  Long id;

  public RevenueDTOResponse(Revenue revenue) {
    this.amount = revenue.getAmount();
    this.date = revenue.getDate();
    this.description = revenue.getDescription();
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
