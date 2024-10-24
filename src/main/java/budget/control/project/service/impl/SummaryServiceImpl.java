package budget.control.project.service.impl;

import budget.control.project.dto.response.MonthlySummaryDTOResponse;
import budget.control.project.repository.ExpenseRepository;
import budget.control.project.repository.RevenueRepository;
import budget.control.project.service.SummaryService;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class SummaryServiceImpl implements SummaryService {

  private final ExpenseRepository expenseRepository;

  private final RevenueRepository revenueRepository;

  public SummaryServiceImpl(ExpenseRepository expenseRepository, RevenueRepository revenueRepository) {
    this.expenseRepository = expenseRepository;
    this.revenueRepository = revenueRepository;
  }

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
