package budget.control.project.model;

import budget.control.project.dto.RevenueDTORequest;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "revenue")
public class Revenue {

  private double amount;
  private LocalDate date;
  private String description;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  public Revenue(RevenueDTORequest revenueDTORequest) {
    this.amount = revenueDTORequest.getAmount();
    this.category = revenueDTORequest.getCategory();
    this.date = revenueDTORequest.getDate();
    this.description = revenueDTORequest.getDescription();
  }

  public Revenue(String description, double amount, LocalDate date, Category category) {
    this.amount = amount;
    this.category = category;
    this.date = date;
    this.description = description;
  }

  public void update(RevenueDTORequest revenueDTORequest, Category category) {
    this.amount = revenueDTORequest.getAmount();
    this.category = category;
    this.date = revenueDTORequest.getDate();
    this.description = revenueDTORequest.getDescription();
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
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

  public Long getId() {
    return id;
  }
}
