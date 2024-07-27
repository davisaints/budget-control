package budget.control.project.dto.response;

import budget.control.project.utils.BigDecimalUtil;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.List;

@JsonPropertyOrder({"totalRevenues", "totalExpenses", "finalBalance", "totalSpentPerCategory"})
public class MonthlySummaryDTOResponse {

  private BigDecimal totalExpenses;

  private BigDecimal totalRevenues;

  private List<CategoryExpenseDTOResponse> categoryExpenseSummaries;

  private BigDecimal finalBalance;

  public MonthlySummaryDTOResponse(
      BigDecimal finalBalance,
      BigDecimal totalExpenses,
      BigDecimal totalRevenues,
      List<CategoryExpenseDTOResponse> categoryExpenseSummaries) {
    this.finalBalance = BigDecimalUtil.roundWithCeiling(finalBalance);
    this.totalExpenses = BigDecimalUtil.roundWithCeiling(totalExpenses);
    this.totalRevenues = BigDecimalUtil.roundWithCeiling(totalRevenues);
    this.categoryExpenseSummaries = categoryExpenseSummaries;
  }

  public List<CategoryExpenseDTOResponse> getCategoryExpenseSummaries() {
    return categoryExpenseSummaries;
  }

  public BigDecimal getFinalBalance() {
    return finalBalance;
  }

  public BigDecimal getTotalExpenses() {
    return totalExpenses;
  }

  public BigDecimal getTotalRevenues() {
    return totalRevenues;
  }
}
