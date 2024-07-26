package budget.control.project.service;

import budget.control.project.dto.ExpenseDTORequest;
import budget.control.project.dto.ExpenseDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExpenseService {

  void deleteExpense(Long id);

  Page<ExpenseDTOResponse> findAll(String description, Pageable pageable);

  ExpenseDTOResponse findById(Long id);

  Page<ExpenseDTOResponse> findByYearAndMonth(Integer year, Integer month, Pageable pageable);

  ExpenseDTOResponse postExpense(ExpenseDTORequest expenseDTORequest);

  ExpenseDTOResponse putExpense(ExpenseDTORequest expenseDTORequest, Long id);
}
