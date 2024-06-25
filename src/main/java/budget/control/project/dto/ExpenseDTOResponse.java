package budget.control.project.dto;

import budget.control.project.model.Revenue;

import java.time.LocalDate;

public class ExpenseDTOResponse {

    double amount;
    LocalDate date;
    String description;

    public ExpenseDTOResponse(Revenue revenue) {
        this.amount = revenue.getAmount();
        this.date = revenue.getDate();
        this.description = revenue.getDescription();
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