package budget.control.project.model;

import budget.control.project.dto.ExpenseDTORequest;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "expense")
public class Expense {

  private double amount;
  private LocalDate date;
  private String description;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  public Expense(ExpenseDTORequest expenseDTORequest) {
    this.amount = expenseDTORequest.getAmount();
    this.category = expenseDTORequest.getCategory();
    this.date = expenseDTORequest.getDate();
    this.description = expenseDTORequest.getDescription();
  }

  public Expense(double amount, LocalDate date, String description, Category category) {
    this.amount = amount;
    this.category = category;
    this.date = date;
    this.description = description;
  }

  public void update(ExpenseDTORequest expenseDTORequest, Category category) {
    this.amount = expenseDTORequest.getAmount();
    this.category = category;
    this.date = expenseDTORequest.getDate();
    this.description = expenseDTORequest.getDescription();
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
