package budget.control.project.service.impl;

import budget.control.project.dto.MonthlySummaryDTOResponse;
import budget.control.project.repository.ExpenseRepository;
import budget.control.project.repository.RevenueRepository;
import budget.control.project.service.SummaryService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummaryServiceImpl implements SummaryService {

  @Autowired ExpenseRepository expenseRepository;
  @Autowired RevenueRepository revenueRepository;

  public MonthlySummaryDTOResponse findMonthlySummary(Integer year, Integer month) {
    BigDecimal totalExpense = expenseRepository.findTotalMonthlyExpense(year, month);
    BigDecimal totalRevenue = revenueRepository.findTotalMonthlyRevenue(year, month);

    BigDecimal finalBalance = totalRevenue.subtract(totalExpense);

    return new MonthlySummaryDTOResponse(
        finalBalance,
        totalExpense,
        totalRevenue,
        expenseRepository.findTotalExpensesPerCategory(year, month));
  }
}
