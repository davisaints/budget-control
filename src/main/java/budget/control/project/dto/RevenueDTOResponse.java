package budget.control.project.dto;

import budget.control.project.model.Revenue;

import java.time.LocalDate;

public class RevenueDTOResponse {
    Long id;
    String description;
    double amount;
    LocalDate date;

    public RevenueDTOResponse(Revenue revenue) {
        this.description = revenue.getDescription();
        this.amount = revenue.getAmount();
        this.date = revenue.getDate();
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