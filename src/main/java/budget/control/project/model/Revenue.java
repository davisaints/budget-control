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

    private double amount;
    private LocalDate date;
    private String description;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Revenue(RevenueDTORequest revenueDTORequest) {
        this.amount = revenueDTORequest.getAmount();
        this.date = revenueDTORequest.getDate();
        this.description = revenueDTORequest.getDescription();
    }

    public Revenue(String description, double amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public double getAmount() {
        return amount;
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

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
