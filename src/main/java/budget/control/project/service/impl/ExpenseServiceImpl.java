package budget.control.project.service.impl;

import budget.control.project.dto.request.ExpenseDTORequest;
import budget.control.project.dto.response.ExpenseDTOResponse;
import budget.control.project.dto.response.PaginationDTOResponse;
import budget.control.project.exception.DuplicateExpenseException;
import budget.control.project.exception.InvalidCategoryException;
import budget.control.project.model.Category;
import budget.control.project.model.Expense;
import budget.control.project.repository.CategoryRepository;
import budget.control.project.repository.ExpenseRepository;
import budget.control.project.service.ExpenseService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  private final ExpenseRepository expenseRepository;

  private final CategoryRepository categoryRepository;

  public ExpenseServiceImpl(CategoryRepository categoryRepository, ExpenseRepository expenseRepository) {
    this.expenseRepository = expenseRepository;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public void deleteExpense(Long id) {
    Expense expense =
        expenseRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Expense not found with id: " + id));

    expenseRepository.delete(expense);
  }

  @Override
  public PaginationDTOResponse<ExpenseDTOResponse> findAll(String description, Pageable pageable) {
    Page<ExpenseDTOResponse> expenseDTOResponsePage;

    expenseDTOResponsePage =
        (description == null)
            ? expenseRepository.findAll(pageable).map(ExpenseDTOResponse::new)
            : expenseRepository
                .findByDescriptionContaining(description, pageable)
                .map(ExpenseDTOResponse::new);

    return new PaginationDTOResponse<ExpenseDTOResponse>()
        .builder()
        .setContent(expenseDTOResponsePage.getContent())
        .setPage(expenseDTOResponsePage.getNumber() + 1)
        .setSize(expenseDTOResponsePage.getSize())
        .setTotalElements(expenseDTOResponsePage.getTotalElements())
        .setTotalPages(expenseDTOResponsePage.getTotalPages())
        .setLast(expenseDTOResponsePage.isLast())
        .build();
  }

  @Override
  public ExpenseDTOResponse findById(Long id) {
    Expense expense =
        expenseRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Expense not found with id: " + id));

    return new ExpenseDTOResponse(expense);
  }

  @Override
  public PaginationDTOResponse<ExpenseDTOResponse> findByYearAndMonth(
      Integer year, Integer month, Pageable pageable) {
    if (year == null || month == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Year and month must be provided");
    }

    Page<ExpenseDTOResponse> expenseDTOResponsePage =
        expenseRepository.findByYearAndMonth(year, month, pageable).map(ExpenseDTOResponse::new);

    return new PaginationDTOResponse<ExpenseDTOResponse>()
        .builder()
        .setContent(expenseDTOResponsePage.getContent())
        .setPage(expenseDTOResponsePage.getNumber() + 1)
        .setSize(expenseDTOResponsePage.getSize())
        .setTotalElements(expenseDTOResponsePage.getTotalElements())
        .setTotalPages(expenseDTOResponsePage.getTotalPages())
        .setLast(expenseDTOResponsePage.isLast())
        .build();
  }

  @Override
  public ExpenseDTOResponse postExpense(ExpenseDTORequest expenseDTORequest) {
    if (expenseRepository.findByDescriptionAndTransactionDate(
            expenseDTORequest.getDescription(), expenseDTORequest.getTransactionDate())
        != null) {
      throw new DuplicateExpenseException(
          "Expense with the given description and transaction date already exists");
    }

    validateCategory(expenseDTORequest);

    Expense expense = expenseRepository.save(new Expense(expenseDTORequest));

    return new ExpenseDTOResponse(expense);
  }

  @Override
  public ExpenseDTOResponse putExpense(ExpenseDTORequest expenseDTORequest, Long id) {
    Expense existingExpense =
        expenseRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Expense not found with id: " + id));

    Expense duplicateExpense =
        expenseRepository.findByDescriptionAndTransactionDate(
            expenseDTORequest.getDescription(), expenseDTORequest.getTransactionDate());

    if (duplicateExpense != null && !Objects.equals(duplicateExpense.getId(), id)) {
      throw new DuplicateExpenseException(
          "An expense with the same description and month already exists");
    }

    validateCategory(expenseDTORequest);

    existingExpense.update(expenseDTORequest, expenseDTORequest.getCategory());

    expenseRepository.save(existingExpense);

    return new ExpenseDTOResponse(existingExpense);
  }

  private void validateCategory(ExpenseDTORequest expenseDTORequest) {
    String categoryName = expenseDTORequest.getCategoryName();

    if (categoryName == null || categoryName.isEmpty()) {
      expenseDTORequest.setCategory(categoryRepository.findByName("Other"));
      return;
    }

    categoryRepository
        .findByNameIgnoreCase(categoryName)
        .ifPresentOrElse(
            expenseDTORequest::setCategory,
            () -> {
              String validCategories =
                  categoryRepository.findAll().stream()
                      .map(Category::getName)
                      .collect(Collectors.joining(", "));
              throw new InvalidCategoryException(
                  "Invalid category: "
                      + categoryName
                      + ". Valid categories are: "
                      + validCategories);
            });
  }
}
