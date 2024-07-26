package budget.control.project.dto;

import budget.control.project.utils.BigDecimalUtil;
import java.math.BigDecimal;

public class CategoryExpenseDTOResponse {

  private String categoryName;

  private BigDecimal totalExpense;

  public CategoryExpenseDTOResponse(String categoryName, BigDecimal totalExpense) {
    this.categoryName = categoryName;
    this.totalExpense = BigDecimalUtil.roundWithCeiling(totalExpense);
  }

  public String getCategoryName() {
    return categoryName;
  }

  public BigDecimal getTotalExpense() {
    return totalExpense;
  }
}
