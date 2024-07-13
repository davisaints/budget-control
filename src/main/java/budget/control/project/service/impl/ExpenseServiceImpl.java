package budget.control.project.service.impl;

import budget.control.project.dto.ExpenseDTORequest;
import budget.control.project.dto.ExpenseDTOResponse;
import budget.control.project.exception.DuplicateRevenueException;
import budget.control.project.model.Expense;
import budget.control.project.repository.CategoryRepository;
import budget.control.project.repository.ExpenseRepository;
import budget.control.project.service.ExpenseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
  public Page<ExpenseDTOResponse> getAll(Pageable pageable) {
    return expenseRepository.findAll(pageable).map(ExpenseDTOResponse::new);
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
  public ExpenseDTOResponse post(ExpenseDTORequest expenseDTORequest) {
    if (expenseRepository.findByDescriptionAndDate(
            expenseDTORequest.getDescription(), expenseDTORequest.getDate())
        != null) {
      throw new DuplicateRevenueException(
          "Duplicate entries with an existing description and month are not allowed");
    }

    if (expenseDTORequest.getCategoryName() == null) {
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
    Expense expense =
        expenseRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Expense not found with id: " + id));

    if (expenseRepository.findByDescriptionAndDate(
            expenseDTORequest.getDescription(), expenseDTORequest.getDate())
        != null) {
      throw new DuplicateRevenueException(
          "Duplicate entries with an existing description and month are not allowed");
    }

    if (expenseDTORequest.getCategoryName() == null) {
      expenseDTORequest.setCategory(categoryRepository.findByName("Other"));
    } else {
      expenseDTORequest.setCategory(
          categoryRepository.findByNameIgnoreCase(expenseDTORequest.getCategoryName()));
    }

    expense.update(expenseDTORequest, expenseDTORequest.getCategory());

    expenseRepository.save(expense);

    return new ExpenseDTOResponse(expense);
  }
}
