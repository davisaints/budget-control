package budget.control.project.dto;

import budget.control.project.enums.Category;
import budget.control.project.model.Revenue;
import java.time.LocalDate;

public class RevenueDTOResponse {

  double amount;
  LocalDate date;
  String description;
  Category category;
  Long id;

  public RevenueDTOResponse(Revenue revenue) {
    this.amount = revenue.getAmount();
    this.date = revenue.getDate();
    this.description = revenue.getDescription();
    this.category = revenue.getCategory();
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

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }
}
