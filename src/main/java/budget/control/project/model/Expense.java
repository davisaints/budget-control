package budget.control.project.model;

import budget.control.project.dto.RevenueDTORequest;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Expense")
@Table(name = "expenses")
public class Expense {

    private double amount;
    private LocalDate date;
    private String description;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Expense(RevenueDTORequest revenueDTORequest) {
        this.amount = revenueDTORequest.getAmount();
        this.date = revenueDTORequest.getDate();
        this.description = revenueDTORequest.getDescription();
    }

    public Expense(double amount, LocalDate date, String description) {
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

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

    public Long getId() {
        return id;
    }

}