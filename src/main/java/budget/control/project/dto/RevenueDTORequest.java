package budget.control.project.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public class RevenueDTORequest {
    Long id;
    @NotEmpty @Length(min = 2)
    String description;
    @DecimalMin(value = "0.50")
    double amount;
    @PastOrPresent
    LocalDate date;

    public RevenueDTORequest(String description, double amount, LocalDate date) {
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

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }
}