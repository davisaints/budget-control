package budget.control.project.model;

import budget.control.project.dto.RevenueDTORequest;
import budget.control.project.utils.BigDecimalUtil;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "revenue")
public class Revenue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private BigDecimal amount = BigDecimal.ZERO;

  private String description;

  @Column(name = "transaction_date")
  private LocalDate transactionDate;

  public Revenue(RevenueDTORequest revenueDTORequest) {
    this.amount = BigDecimalUtil.roundWithCeiling(revenueDTORequest.getAmount());
    this.description = revenueDTORequest.getDescription();
    this.transactionDate = revenueDTORequest.getTransactionDate();
  }

  public Revenue(BigDecimal amount, String description, LocalDate transactionDate) {
    this.amount = BigDecimalUtil.roundWithCeiling(amount);
    this.description = description;
    this.transactionDate = transactionDate;
  }

  public Revenue() {}

  public void update(RevenueDTORequest revenueDTORequest) {
    this.amount = BigDecimalUtil.roundWithCeiling(revenueDTORequest.getAmount());
    this.description = revenueDTORequest.getDescription();
    this.transactionDate = revenueDTORequest.getTransactionDate();
  }

  public BigDecimal getAmount() {
    return amount;
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
    Revenue revenue = (Revenue) o;
    return Objects.equals(id, revenue.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
