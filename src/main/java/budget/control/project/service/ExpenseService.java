package budget.control.project.service;

import budget.control.project.dto.ExpenseDTORequest;
import budget.control.project.dto.ExpenseDTOResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseService {

    List<ExpenseDTOResponse> getAll(Pageable pageable);

    ExpenseDTOResponse getById(Long id);

    ExpenseDTOResponse post(ExpenseDTORequest expenseDTORequest) throws DuplicateExpenseException;

    ExpenseDTOResponse put(ExpenseDTORequest expenseDTORequest, Long id);

}
