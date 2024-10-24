package budget.control.project.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import budget.control.project.dto.response.CategoryExpenseDTOResponse;
import budget.control.project.model.Expense;
import budget.control.project.utils.BigDecimalUtil;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class ExpenseRepositoryTest {

  private final ExpenseRepository expenseRepository;

  private final CategoryRepository categoryRepository;

  public ExpenseRepositoryTest(
      CategoryRepository categoryRepository, ExpenseRepository expenseRepository) {
    this.expenseRepository = expenseRepository;
    this.categoryRepository = categoryRepository;
  }

  @BeforeEach
  void setUp() {
    expenseRepository.save(
        new Expense(
            1L,
            BigDecimal.valueOf(50),
            categoryRepository.findByName("Health"),
            "Dental check-up",
            LocalDate.of(2020, 10, 30)));
  }

  @AfterEach
  void tearDown() {
    expenseRepository.deleteAll();
  }

  @Test
  void findByYearAndMonth() {
    expenseRepository.save(
        new Expense(
            2L,
            BigDecimal.valueOf(100),
            categoryRepository.findByName("Food"),
            "Birthday dinner",
            LocalDate.of(2020, 10, 30)));

    Pageable pageable = PageRequest.of(0, 2);

    Page<Expense> expensePage = expenseRepository.findByYearAndMonth(2020, 10, pageable);

    assertThat(expensePage)
        .extracting(Expense::getDescription)
        .containsExactlyInAnyOrder("Dental check-up", "Birthday dinner");
  }

  @Test
  void findByDescriptionAndTransactionDate() {
    Expense expense =
        expenseRepository.findByDescriptionAndTransactionDate(
            "Dental check-up", LocalDate.of(2020, 10, 30));

    Expense expenseWithIgnoreCase =
        expenseRepository.findByDescriptionAndTransactionDate(
            "dental check-up", LocalDate.of(2020, 10, 30));

    assertThat(expense).isNotNull();
    assertThat(expenseWithIgnoreCase).isNotNull();
  }

  @Test
  void findByDescriptionContaining() {
    expenseRepository.save(
        new Expense(
            2L,
            BigDecimal.valueOf(100),
            categoryRepository.findByName("Health"),
            "Medical check-up",
            LocalDate.of(2020, 10, 30)));

    Pageable pageable = PageRequest.of(0, 2);

    Page<Expense> expensePage = expenseRepository.findByDescriptionContaining("check-up", pageable);

    assertThat(expensePage).hasSize(2);
  }

  @Test
  void findTotalMonthlyExpense() {
    expenseRepository.save(
        new Expense(
            2L,
            BigDecimal.valueOf(50),
            categoryRepository.findByName("Food"),
            "Birthday dinner",
            LocalDate.of(2020, 10, 30)));

    BigDecimal monthlyExpense = expenseRepository.findTotalMonthlyExpense(2020, 10);

    assertThat(BigDecimalUtil.roundWithCeiling(monthlyExpense))
        .isEqualTo(BigDecimalUtil.roundWithCeiling(BigDecimal.valueOf(100)));
  }

  @Test
  void findTotalExpensesPerCategory() {
    List<CategoryExpenseDTOResponse> categoryExpenseDTOResponseList =
        expenseRepository.findTotalExpensesPerCategory(2020, 10);

    assertThat(categoryExpenseDTOResponseList).hasSize(1);
    assertEquals("Health", categoryExpenseDTOResponseList.get(0).getCategoryName());
    assertEquals(
        categoryExpenseDTOResponseList.get(0).getTotalExpense(),
        BigDecimalUtil.roundWithCeiling(BigDecimal.valueOf(50)));
  }
}
