package budget.control.project.model;

import budget.control.project.dto.request.ExpenseDTORequest;
import budget.control.project.utils.BigDecimalUtil;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "expense")
public class Expense {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private BigDecimal amount = BigDecimal.ZERO;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  private String description;

  @Column(name = "transaction_date")
  private LocalDate transactionDate;

  public Expense(ExpenseDTORequest expenseDTORequest) {
    this.amount = BigDecimalUtil.roundWithCeiling(expenseDTORequest.getAmount());
    this.category = expenseDTORequest.getCategory();
    this.description = expenseDTORequest.getDescription();
    this.transactionDate = expenseDTORequest.getTransactionDate();
  }

  public Expense(
      Long id,
      BigDecimal amount,
      Category category,
      String description,
      LocalDate transactionDate) {
    this.id = id;
    this.amount = BigDecimalUtil.roundWithCeiling(amount);
    this.category = category;
    this.description = description;
    this.transactionDate = transactionDate;
  }

  public Expense() {}

  public void update(ExpenseDTORequest expenseDTORequest, Category category) {
    this.amount = BigDecimalUtil.roundWithCeiling(expenseDTORequest.getAmount());
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

  public void setCategory(Category category) {
    this.category = category;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Expense expense = (Expense) o;
    return Objects.equals(id, expense.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
