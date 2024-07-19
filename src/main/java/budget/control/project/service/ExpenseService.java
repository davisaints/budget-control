package budget.control.project.service;

import budget.control.project.dto.ExpenseDTORequest;
import budget.control.project.dto.ExpenseDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExpenseService {

  void delete(Long id);

  Page<ExpenseDTOResponse> getAll(String description, Pageable pageable);

  ExpenseDTOResponse getById(Long id);

  Page<ExpenseDTOResponse> getByYearAndMonth(Integer year, Integer month, Pageable pageable);

  ExpenseDTOResponse post(ExpenseDTORequest expenseDTORequest);

  ExpenseDTOResponse put(ExpenseDTORequest expenseDTORequest, Long id);
}
