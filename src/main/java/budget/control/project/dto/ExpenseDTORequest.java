package budget.control.project.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;

public class ExpenseDTORequest {

  @DecimalMin(value = "0.50")
  double amount;

  @PastOrPresent LocalDate date;

  @NotEmpty
  @Length(min = 2)
  String description;

  public ExpenseDTORequest(double amount, LocalDate date, String description) {
    this.amount = amount;
    this.date = date;
    this.description = description;
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
}
