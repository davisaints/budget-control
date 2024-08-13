package budget.control.project.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import budget.control.project.dto.request.ExpenseDTORequest;
import budget.control.project.dto.response.ExpenseDTOResponse;
import budget.control.project.exception.DuplicateExpenseException;
import budget.control.project.model.Category;
import budget.control.project.model.Expense;
import budget.control.project.repository.CategoryRepository;
import budget.control.project.repository.ExpenseRepository;
import budget.control.project.service.impl.ExpenseServiceImpl;
import budget.control.project.utils.BigDecimalUtil;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

  @Mock private ExpenseRepository expenseRepository;
  @Mock private CategoryRepository categoryRepository;
  @InjectMocks private ExpenseServiceImpl expenseService;
  private ExpenseDTORequest expenseDTORequest;
  private Category category;
  private Expense expense;

  @BeforeEach
  void setUp() {
    category = new Category("Food", 1L);

    expenseDTORequest =
        new ExpenseDTORequest(
            BigDecimalUtil.roundWithCeiling(BigDecimal.valueOf(100)),
            "Food",
            "Lunch",
            LocalDate.of(2020, 10, 10));

    expense = new Expense(expenseDTORequest);
  }

  @AfterEach
  void tearDown() {}

  @Test
  void whenSavingValidExpense_thenShouldReturnMatchingDtoResponse() {
    // Given a valid expense
    given(
            expenseRepository.findByDescriptionAndTransactionDate(
                expenseDTORequest.getDescription(), expenseDTORequest.getTransactionDate()))
        .willReturn(null);
    given(categoryRepository.findByNameIgnoreCase(category.getName()))
        .willReturn(Optional.of(category));
    given(expenseRepository.save(expense)).willReturn(expense);

    // When the expense is saved
    ExpenseDTOResponse expenseDTOResponse = expenseService.postExpense(expenseDTORequest);

    // Then the saved expense values should match the values in the expense DTO response
    assertNotNull(expenseDTOResponse);
    assertEquals(expense.getAmount(), expenseDTOResponse.getAmount());
    assertEquals(expense.getCategory(), expenseDTOResponse.getCategory());
    assertEquals(expense.getDescription(), expenseDTOResponse.getDescription());
    assertEquals(expense.getTransactionDate(), expenseDTOResponse.getTransactionDate());
  }
}
