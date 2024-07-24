package budget.control.project.model;

import budget.control.project.dto.ExpenseDTORequest;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "expense")
public class Expense {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private BigDecimal amount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  private String description;

  @Column(name = "transaction_date")
  private LocalDate transactionDate;

  public Expense(ExpenseDTORequest expenseDTORequest) {
    this.amount = expenseDTORequest.getAmount();
    this.category = expenseDTORequest.getCategory();
    this.description = expenseDTORequest.getDescription();
    this.transactionDate = expenseDTORequest.getTransactionDate();
  }

  public Expense(
      BigDecimal amount, Category category, String description, LocalDate transactionDate) {
    this.amount = amount;
    this.category = category;
    this.description = description;
    this.transactionDate = transactionDate;
  }

  public Expense() {}

  public void update(ExpenseDTORequest expenseDTORequest, Category category) {
    this.amount = expenseDTORequest.getAmount();
    this.category = category;
    this.description = expenseDTORequest.getDescription();
    this.transactionDate = expenseDTORequest.getTransactionDate();
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Category getCategory() {
    return category;
  }

  public String getDescription() {
    return description;
  }

  public Long getId() {
    return id;
  }

  public LocalDate getTransactionDate() {
    return transactionDate;
  }
}
