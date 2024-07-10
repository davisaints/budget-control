package budget.control.project.model;

import budget.control.project.dto.RevenueDTORequest;
import budget.control.project.enums.Category;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
@Entity(name = "Revenue")
@Table(name = "revenues")
public class Revenue {

  @Enumerated(EnumType.STRING)
  private Category category;

  private double amount;
  private LocalDate date;
  private String description;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public Revenue(RevenueDTORequest revenueDTORequest) {
    this.amount = revenueDTORequest.getAmount();
    this.date = revenueDTORequest.getDate();
    this.description = revenueDTORequest.getDescription();
    this.category = revenueDTORequest.getCategory();
  }

  public Revenue(double amount, LocalDate date, String description, Category category) {
    this.amount = amount;
    this.date = date;
    this.description = description;
    this.category = category;
  }

  public Revenue() {}

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
}
