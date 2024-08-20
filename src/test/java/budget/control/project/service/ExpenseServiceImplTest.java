package budget.control.project.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import budget.control.project.dto.request.ExpenseDTORequest;
import budget.control.project.dto.response.ExpenseDTOResponse;
import budget.control.project.exception.DuplicateExpenseException;
import budget.control.project.exception.InvalidCategoryException;
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

  Category defaultCategory;
  @Mock private ExpenseRepository expenseRepository;
  @Mock private CategoryRepository categoryRepository;
  @InjectMocks private ExpenseServiceImpl expenseService;

  @BeforeEach
  void setup() {
    defaultCategory = new Category("Other", 2L);
  }

  @AfterEach
  void tearDown() {
    expenseRepository.deleteAll();
  }

  @Test
  void givenDuplicateExpense_whenSaving_thenShouldThrowException() {
    // Arrange
    ExpenseDTORequest duplicatedExpense =
        new ExpenseDTORequest(
            BigDecimalUtil.roundWithCeiling(BigDecimal.valueOf(100)),
            "Food",
            "Lunch",
            LocalDate.of(2020, 10, 10));

    Expense expense = new Expense(duplicatedExpense);

    given(
            expenseRepository.findByDescriptionAndTransactionDate(
                duplicatedExpense.getDescription(), duplicatedExpense.getTransactionDate()))
        .willReturn(expense);

    // Act
    DuplicateExpenseException duplicateExpenseException =
        assertThrows(
            DuplicateExpenseException.class,
            () -> {
              expenseService.postExpense(duplicatedExpense);
            });

    // Assert
    assertEquals(
        "Duplicate entries with an existing description and month are not allowed",
        duplicateExpenseException.getMessage());
  }

  @Test
  void givenValidExpense_whenSaving_thenShouldReturnMatchingDtoResponse() {
    // Arrange
    ExpenseDTORequest request =
        new ExpenseDTORequest(
            BigDecimalUtil.roundWithCeiling(BigDecimal.valueOf(100)),
            "Food",
            "Lunch",
            LocalDate.of(2020, 10, 10));

    Expense expense = new Expense(request);

    Category category = new Category("Food", 1L);

    given(
            expenseRepository.findByDescriptionAndTransactionDate(
                request.getDescription(), request.getTransactionDate()))
        .willReturn(null);
    given(categoryRepository.findByNameIgnoreCase(category.getName()))
        .willReturn(Optional.of(category));
    given(expenseRepository.save(expense)).willReturn(expense);

    // Act
    ExpenseDTOResponse response = expenseService.postExpense(request);

    // Assert
    assertNotNull(response);
    assertEquals(expense.getAmount(), response.getAmount());
    assertEquals(expense.getCategory(), response.getCategory());
    assertEquals(expense.getDescription(), response.getDescription());
    assertEquals(expense.getTransactionDate(), response.getTransactionDate());
  }

  @Test
  void givenNullCategoryName_whenSaving_thenShouldSetDefaultCategory() {
    // Arrange
    ExpenseDTORequest request =
        new ExpenseDTORequest(
            BigDecimalUtil.roundWithCeiling(BigDecimal.valueOf(100)),
            null,
            "Lunch",
            LocalDate.of(2020, 10, 10));

    Expense expense = new Expense(request);
    expense.setCategory(defaultCategory);

    given(
            expenseRepository.findByDescriptionAndTransactionDate(
                request.getDescription(), request.getTransactionDate()))
        .willReturn(null);
    given(categoryRepository.findByName("Other")).willReturn(defaultCategory);
    given(expenseRepository.save(expense)).willReturn(expense);

    // Act
    ExpenseDTOResponse response = expenseService.postExpense(request);

    // Assert
    assertNotNull(response);
    assertEquals(defaultCategory, response.getCategory());
    then(categoryRepository).should().findByName("Other");
  }

  @Test
  void givenEmptyCategoryName_whenSaving_thenShouldSetDefaultCategory() {
    // Arrange
    ExpenseDTORequest request =
        new ExpenseDTORequest(
            BigDecimalUtil.roundWithCeiling(BigDecimal.valueOf(100)),
            "",
            "Lunch",
            LocalDate.of(2020, 10, 10));

    Expense expense = new Expense(request);
    expense.setCategory(defaultCategory);

    given(
            expenseRepository.findByDescriptionAndTransactionDate(
                request.getDescription(), request.getTransactionDate()))
        .willReturn(null);
    given(categoryRepository.findByName("Other")).willReturn(defaultCategory);
    given(expenseRepository.save(expense)).willReturn(expense);

    // Act
    ExpenseDTOResponse response = expenseService.postExpense(request);

    // Assert
    assertNotNull(response);
    assertEquals(defaultCategory, response.getCategory());
    then(categoryRepository).should().findByName("Other");
  }

  @Test
  void givenInvalidCategoryName_whenSaving_thenShouldThrowException() {
    // Arrange
    ExpenseDTORequest request =
        new ExpenseDTORequest(
            BigDecimalUtil.roundWithCeiling(BigDecimal.valueOf(100)),
            "Invalid category",
            "Lunch",
            LocalDate.of(2020, 10, 10));

    given(categoryRepository.findByNameIgnoreCase("Invalid category"))
        .willThrow(InvalidCategoryException.class);

    // Act & Assert
    assertThrows(
        InvalidCategoryException.class,
        () -> {
          expenseService.postExpense(request);
        });

    then(expenseRepository).should(never()).save(new Expense(request));
  }
}
