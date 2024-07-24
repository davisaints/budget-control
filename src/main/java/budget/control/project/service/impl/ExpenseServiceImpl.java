package budget.control.project.service.impl;

import budget.control.project.dto.ExpenseDTORequest;
import budget.control.project.dto.ExpenseDTOResponse;
import budget.control.project.exception.DuplicateRevenueException;
import budget.control.project.model.Expense;
import budget.control.project.repository.CategoryRepository;
import budget.control.project.repository.ExpenseRepository;
import budget.control.project.service.ExpenseService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  @Autowired CategoryRepository categoryRepository;
  @Autowired ExpenseRepository expenseRepository;

  @Override
  public void delete(Long id) {
    Expense expense =
        expenseRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Expense not found with id: " + id));

    expenseRepository.delete(expense);
  }

  @Override
  public Page<ExpenseDTOResponse> getAll(String description, Pageable pageable) {
    if (description == null) {
      return expenseRepository.findAll(pageable).map(ExpenseDTOResponse::new);
    } else {
      return expenseRepository
          .findByDescriptionContaining(description, pageable)
          .map(ExpenseDTOResponse::new);
    }
  }

  @Override
  public ExpenseDTOResponse getById(Long id) {
    Expense expense =
        expenseRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Expense not found with id: " + id));

    return new ExpenseDTOResponse(expense);
  }

  @Override
  public Page<ExpenseDTOResponse> getByYearAndMonth(
      Integer year, Integer month, Pageable pageable) {
    if (year == null || month == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Year and month must be provided");
    }

    return expenseRepository.findByYearAndMonth(year, month, pageable).map(ExpenseDTOResponse::new);
  }

  @Override
  public ExpenseDTOResponse post(ExpenseDTORequest expenseDTORequest) {
    if (expenseRepository.findByDescriptionAndTransactionDate(
            expenseDTORequest.getDescription(), expenseDTORequest.getTransactionDate())
        != null) {
      throw new DuplicateRevenueException(
          "Duplicate entries with an existing description and month are not allowed");
    }

    if (expenseDTORequest.getCategoryName() == null
        || expenseDTORequest.getCategoryName().isEmpty()) {
      expenseDTORequest.setCategory(categoryRepository.findByName("Other"));
    } else {
      expenseDTORequest.setCategory(
          categoryRepository.findByNameIgnoreCase(expenseDTORequest.getCategoryName()));
    }

    Expense expense = expenseRepository.save(new Expense(expenseDTORequest));

    return new ExpenseDTOResponse(expense);
  }

  @Override
  public ExpenseDTOResponse put(ExpenseDTORequest expenseDTORequest, Long id) {
    Expense existingExpense =
        expenseRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Expense not found with id: " + id));

    Expense duplicateExpense =
        expenseRepository.findByDescriptionAndTransactionDate(
            expenseDTORequest.getDescription(), expenseDTORequest.getTransactionDate());

    if (duplicateExpense != null && !Objects.equals(duplicateExpense.getId(), id)) {
      throw new DuplicateRevenueException(
          "Duplicate entries with an existing description and month are not allowed");
    }

    if (expenseDTORequest.getCategoryName() == null
        || expenseDTORequest.getCategoryName().isEmpty()) {
      expenseDTORequest.setCategory(categoryRepository.findByName("Other"));
    } else {
      expenseDTORequest.setCategory(
          categoryRepository.findByNameIgnoreCase(expenseDTORequest.getCategoryName()));
    }

    existingExpense.update(expenseDTORequest, expenseDTORequest.getCategory());

    expenseRepository.save(existingExpense);

    return new ExpenseDTOResponse(existingExpense);
  }
}
