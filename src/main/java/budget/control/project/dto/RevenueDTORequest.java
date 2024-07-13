package budget.control.project.dto;

import budget.control.project.model.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;

public class RevenueDTORequest {

  @DecimalMin(value = "0.50")
  double amount;

  Category category;

  String categoryName;

  @PastOrPresent LocalDate date;

  @NotEmpty
  @Length(min = 2)
  String description;

  public RevenueDTORequest(double amount, String categoryName, LocalDate date, String description) {
    this.description = description;
    this.amount = amount;
    this.date = date;
    this.categoryName = categoryName;
  }

  public double getAmount() {
    return amount;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public LocalDate getDate() {
    return date;
  }

  public String getDescription() {
    return description;
  }
}
