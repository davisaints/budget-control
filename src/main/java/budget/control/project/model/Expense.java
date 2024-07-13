package budget.control.project.model;

import budget.control.project.dto.ExpenseDTORequest;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "expense")
public class Expense {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private double amount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  private LocalDate date;
  
  private String description;

  public Expense(ExpenseDTORequest expenseDTORequest) {
    this.amount = expenseDTORequest.getAmount();
    this.category = expenseDTORequest.getCategory();
    this.date = expenseDTORequest.getDate();
    this.description = expenseDTORequest.getDescription();
  }

  public Expense(double amount, Category category, LocalDate date, String description) {
    this.amount = amount;
    this.category = category;
    this.date = date;
    this.description = description;
  }

  public Expense() {}

  public void update(ExpenseDTORequest expenseDTORequest, Category category) {
    this.amount = expenseDTORequest.getAmount();
    this.category = category;
    this.date = expenseDTORequest.getDate();
    this.description = expenseDTORequest.getDescription();
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

  public Long getId() {
    return id;
  }
}
