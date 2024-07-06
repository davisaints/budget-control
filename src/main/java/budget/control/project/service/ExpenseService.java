package budget.control.project.service;

import budget.control.project.dto.ExpenseDTORequest;
import budget.control.project.dto.ExpenseDTOResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ExpenseService {

  void delete(Long id);

  List<ExpenseDTOResponse> getAll(Pageable pageable);

  ExpenseDTOResponse getById(Long id);

  ExpenseDTOResponse post(ExpenseDTORequest expenseDTORequest);

  ExpenseDTOResponse put(ExpenseDTORequest expenseDTORequest, Long id);
}
