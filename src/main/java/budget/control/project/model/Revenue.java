package budget.control.project.model;

import budget.control.project.dto.RevenueDTORequest;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "revenue")
public class Revenue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private double amount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  private String description;

  @Column(name = "transaction_date")
  private LocalDate transactionDate;

  public Revenue(RevenueDTORequest revenueDTORequest) {
    this.amount = revenueDTORequest.getAmount();
    this.category = revenueDTORequest.getCategory();
    this.description = revenueDTORequest.getDescription();
    this.transactionDate = revenueDTORequest.getTransactionDate();
  }

  public Revenue(double amount, Category category, String description, LocalDate transactionDate) {
    this.amount = amount;
    this.category = category;
    this.description = description;
    this.transactionDate = transactionDate;
  }

  public Revenue() {}

  public void update(RevenueDTORequest revenueDTORequest, Category category) {
    this.amount = revenueDTORequest.getAmount();
    this.category = category;
    this.description = revenueDTORequest.getDescription();
    this.transactionDate = revenueDTORequest.getTransactionDate();
  }

  public double getAmount() {
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
