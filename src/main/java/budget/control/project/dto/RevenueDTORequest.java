package budget.control.project.dto;

import budget.control.project.enums.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;

public class RevenueDTORequest {

  @DecimalMin(value = "0.50")
  double amount;

  @PastOrPresent LocalDate date;

  @NotEmpty
  @Length(min = 2)
  String description;

  Category category;
  String categoryName;

  public RevenueDTORequest(double amount, LocalDate date, String description, String categoryName) {
    this.description = description;
    this.amount = amount;
    this.date = date;
    this.categoryName = categoryName;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getDate() {
    return date;
  }

  public double getAmount() {
    return amount;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }
}
