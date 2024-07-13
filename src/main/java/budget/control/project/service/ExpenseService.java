package budget.control.project.service;

import budget.control.project.dto.ExpenseDTORequest;
import budget.control.project.dto.ExpenseDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExpenseService {

  void delete(Long id);

  Page<ExpenseDTOResponse> getAll(String description, Pageable pageable);

  ExpenseDTOResponse getById(Long id);

  ExpenseDTOResponse post(ExpenseDTORequest expenseDTORequest);

  ExpenseDTOResponse put(ExpenseDTORequest expenseDTORequest, Long id);
}
