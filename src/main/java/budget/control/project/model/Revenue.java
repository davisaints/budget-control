package budget.control.project.model;

import budget.control.project.dto.RevenueDTORequest;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "revenue")
public class Revenue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private BigDecimal amount;

  private String description;

  @Column(name = "transaction_date")
  private LocalDate transactionDate;

  public Revenue(RevenueDTORequest revenueDTORequest) {
    this.amount = revenueDTORequest.getAmount();
    this.description = revenueDTORequest.getDescription();
    this.transactionDate = revenueDTORequest.getTransactionDate();
  }

  public Revenue(BigDecimal amount, String description, LocalDate transactionDate) {
    this.amount = amount;
    this.description = description;
    this.transactionDate = transactionDate;
  }

  public Revenue() {}

  public void update(RevenueDTORequest revenueDTORequest) {
    this.amount = revenueDTORequest.getAmount();
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
}
