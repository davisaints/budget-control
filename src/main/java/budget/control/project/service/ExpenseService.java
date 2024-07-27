package budget.control.project.service;

import budget.control.project.dto.request.ExpenseDTORequest;
import budget.control.project.dto.response.ExpenseDTOResponse;
import budget.control.project.dto.response.PaginationDTOResponse;
import org.springframework.data.domain.Pageable;

public interface ExpenseService {

  void deleteExpense(Long id);

  PaginationDTOResponse<ExpenseDTOResponse> findAll(String description, Pageable pageable);

  ExpenseDTOResponse findById(Long id);

  PaginationDTOResponse<ExpenseDTOResponse> findByYearAndMonth(
      Integer year, Integer month, Pageable pageable);

  ExpenseDTOResponse postExpense(ExpenseDTORequest expenseDTORequest);

  ExpenseDTOResponse putExpense(ExpenseDTORequest expenseDTORequest, Long id);
}
