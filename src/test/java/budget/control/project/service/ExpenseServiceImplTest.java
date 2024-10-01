package budget.control.project.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import budget.control.project.dto.request.ExpenseDTORequest;
import budget.control.project.dto.response.ExpenseDTOResponse;
import budget.control.project.dto.response.PaginationDTOResponse;
import budget.control.project.exception.DuplicateExpenseException;
import budget.control.project.exception.InvalidCategoryException;
import budget.control.project.model.Category;
import budget.control.project.model.Expense;
import budget.control.project.repository.CategoryRepository;
import budget.control.project.repository.ExpenseRepository;
import budget.control.project.service.impl.ExpenseServiceImpl;
import budget.control.project.utils.BigDecimalUtil;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceImplTest {

  @InjectMocks private ExpenseServiceImpl expenseServiceImpl;

  @Mock private CategoryRepository categoryRepository;

  @Mock private ExpenseRepository expenseRepository;

  private Category category;
  private Category defaultCategory;
  private Expense expense;
  private ExpenseDTORequest request;

  @BeforeEach
  void setup() {
    defaultCategory = new Category("Other", 1L);

    category = new Category("Food", 2L);

    request =
        new ExpenseDTORequest(
            BigDecimalUtil.roundWithCeiling(BigDecimal.valueOf(100)),
            "Food",
            "Lunch",
            LocalDate.of(2020, 10, 10));

    expense = new Expense(request);
  }

  @AfterEach
  void tearDown() {
    expenseRepository.deleteAll();
  }

  @Test
  void givenDescription_whenFindAll_thenReturnFilteredExpenses() {
    // Arrange
    String description = "Lunch";
    Pageable pageable = PageRequest.of(0, 10);
    Page<Expense> expensePage = new PageImpl<>(List.of(expense), pageable, 1);
    given(expenseRepository.findByDescriptionContaining(description, pageable))
        .willReturn(expensePage);

    // Act
    PaginationDTOResponse<ExpenseDTOResponse> result =
        expenseServiceImpl.findAll(description, pageable);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals(1, result.getTotalPages());
    assertTrue(result.isLast());
    then(expenseRepository).should().findByDescriptionContaining(description, pageable);
  }

  @Test
  void givenDuplicateExpense_whenSaving_thenThrowDuplicateExpenseException() {
    // Arrange
    given(
            expenseRepository.findByDescriptionAndTransactionDate(
                request.getDescription(), request.getTransactionDate()))
        .willReturn(expense);

    // Act & Assert
    DuplicateExpenseException exception =
        assertThrows(
            DuplicateExpenseException.class, () -> expenseServiceImpl.postExpense(request));

    // Assert
    assertEquals(
        "Expense with the given description and transaction date already exists",
        exception.getMessage());
  }

  @Test
  void givenEmptyCategoryName_whenSaving_thenSetDefaultCategory() {
    // Arrange
    request.setCategoryName("");
    expense.setCategory(defaultCategory);

    given(
            expenseRepository.findByDescriptionAndTransactionDate(
                request.getDescription(), request.getTransactionDate()))
        .willReturn(null);
    given(categoryRepository.findByName("Other")).willReturn(defaultCategory);
    given(expenseRepository.save(expense)).willReturn(expense);

    // Act
    ExpenseDTOResponse response = expenseServiceImpl.postExpense(request);

    // Assert
    assertNotNull(response);
    assertEquals(defaultCategory, response.getCategory());
    then(categoryRepository).should().findByName("Other");
  }

  @Test
  void givenExpense_whenFindById_thenReturnExpenseDtoResponse() {
    // Arrange
    Long expenseId = 1L;
    given(expenseRepository.findById(expenseId)).willReturn(Optional.of(expense));

    // Act
    ExpenseDTOResponse result = expenseServiceImpl.findById(expenseId);

    // Assert
    assertNotNull(result);
    assertEquals(expense.getAmount(), result.getAmount());
    assertEquals(expense.getCategory(), result.getCategory());
    assertEquals(expense.getDescription(), result.getDescription());
    assertEquals(expense.getTransactionDate(), result.getTransactionDate());
    then(expenseRepository).should().findById(expenseId);
  }

  @Test
  void givenExistingExpense_whenUpdating_thenReturnExpenseDtoResponseWithUpdatedFields() {
    // Arrange
    ExpenseDTORequest updatedExpense =
        new ExpenseDTORequest(
            BigDecimalUtil.roundWithCeiling(BigDecimal.valueOf(200)),
            "Food",
            "Party",
            LocalDate.of(2021, 12, 30));

    // Act
    expense.update(updatedExpense, category);

    // Assert
    assertEquals(expense.getAmount(), updatedExpense.getAmount());
    assertEquals(expense.getDescription(), updatedExpense.getDescription());
    assertEquals(expense.getTransactionDate(), updatedExpense.getTransactionDate());
  }

  @Test
  void givenInvalidCategoryName_whenSaving_thenThrowInvalidCategoryException() {
    // Arrange
    request.setCategoryName("Invalid Category");

    given(categoryRepository.findByNameIgnoreCase(request.getCategoryName()))
        .willReturn(Optional.empty());

    // Act & Assert
    InvalidCategoryException exception =
        assertThrows(
            InvalidCategoryException.class,
            () -> {
              expenseServiceImpl.postExpense(request);
            });

    assertEquals(
        "Invalid category: " + request.getCategoryName() + ". Valid categories are: ",
        exception.getMessage());
    then(expenseRepository).should(never()).save(new Expense(request));
  }

  @Test
  void givenInvalidId_whenFindById_thenThrowEntityNotFoundException() {
    // Arrange
    Long expenseId = 1L;
    given(expenseRepository.findById(expenseId)).willReturn(Optional.empty());

    // Act & Assert
    EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class,
            () -> {
              expenseServiceImpl.findById(expenseId);
            });

    assertEquals("Expense not found with id: " + expenseId, exception.getMessage());
    then(expenseRepository).should().findById(expenseId);
  }

  @Test
  void givenNoDescription_whenFindAll_thenReturnAllExpenses() {
    // Arrange
    Pageable pageable = PageRequest.of(0, 10);
    Page<Expense> expensePage = new PageImpl<>(List.of(expense), pageable, 1);
    given(expenseRepository.findAll(pageable)).willReturn(expensePage);

    // Act
    PaginationDTOResponse<ExpenseDTOResponse> result = expenseServiceImpl.findAll(null, pageable);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals(1, result.getTotalPages());
    assertTrue(result.isLast());
    then(expenseRepository).should().findAll(pageable);
  }

  @Test
  void givenNonExistingId_whenPutExpense_thenThrowEntityNotFoundException() {
    // Arrange
    Long nonExistingId = 1L;
    given(expenseRepository.findById(nonExistingId)).willReturn(Optional.empty());

    // Act
    EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class,
            () -> {
              expenseServiceImpl.putExpense(request, nonExistingId);
            });

    // Assert
    assertEquals("Expense not found with id: " + nonExistingId, exception.getMessage());
  }

  @Test
  void givenNullCategoryName_whenSaving_thenSetDefaultCategory() {
    // Arrange
    request.setCategoryName(null);
    expense.setCategory(defaultCategory);

    given(
            expenseRepository.findByDescriptionAndTransactionDate(
                request.getDescription(), request.getTransactionDate()))
        .willReturn(null);
    given(categoryRepository.findByName("Other")).willReturn(defaultCategory);
    given(expenseRepository.save(expense)).willReturn(expense);

    // Act
    ExpenseDTOResponse response = expenseServiceImpl.postExpense(request);

    // Assert
    assertNotNull(response);
    assertEquals(defaultCategory, response.getCategory());
    then(categoryRepository).should().findByName("Other");
  }

  @Test
  void givenNullYearOrMonth_whenFindByYearAndMonth_thenThrowBadRequestException() {
    // Arrange
    Pageable pageable = PageRequest.of(0, 10);

    // Act & Assert
    ResponseStatusException exception =
        assertThrows(
            ResponseStatusException.class,
            () -> expenseServiceImpl.findByYearAndMonth(null, 10, pageable));

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("Year and month must be provided", exception.getReason());

    exception =
        assertThrows(
            ResponseStatusException.class,
            () -> expenseServiceImpl.findByYearAndMonth(2020, null, pageable));

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("Year and month must be provided", exception.getReason());
  }

  @Test
  void givenSavedExpense_whenDelete_thenRemoveFromDB() {
    // Arrange
    Long expenseId = 1L;
    given(expenseRepository.findById(expenseId)).willReturn(Optional.of(expense));

    // Act
    expenseServiceImpl.deleteExpense(expenseId);

    // Assert
    then(expenseRepository).should().delete(expense);
  }

  @Test
  void givenValidYearAndMonth_whenFindByYearAndMonth_thenReturnFilteredExpenses() {
    // Arrange
    int year = 2020;
    int month = 10;
    Pageable pageable = PageRequest.of(0, 10);
    Page<Expense> expensePage = new PageImpl<>(List.of(expense), pageable, 1);
    given(expenseRepository.findByYearAndMonth(year, month, pageable)).willReturn(expensePage);

    // Act
    PaginationDTOResponse<ExpenseDTOResponse> result =
        expenseServiceImpl.findByYearAndMonth(year, month, pageable);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals(1, result.getTotalPages());
    assertTrue(result.isLast());
    then(expenseRepository).should().findByYearAndMonth(year, month, pageable);
  }
}
