package budget.control.project.model;

import budget.control.project.dto.RevenueDTORequest;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Revenue")
@Table(name = "revenues")
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private double amount;
    private LocalDate date;

    public Revenue(RevenueDTORequest revenueDTORequest) {
        this.description = revenueDTORequest.getDescription();
        this.amount = revenueDTORequest.getAmount();
        this.date = revenueDTORequest.getDate();
    }

    public Revenue(String description, double amount, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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
}
