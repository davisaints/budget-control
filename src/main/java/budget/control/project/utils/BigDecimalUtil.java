package budget.control.project.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {
  public static BigDecimal roundWithCeiling(BigDecimal amount) {
    if (amount != null) {
      return amount.setScale(2, RoundingMode.CEILING);
    } else {
      return null;
    }
  }
}
