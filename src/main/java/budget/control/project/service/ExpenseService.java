package budget.control.project.service;

import budget.control.project.dto.ExpenseDTORequest;
import budget.control.project.dto.ExpenseDTOResponse;
import budget.control.project.exception.DuplicateExpenseException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseService {

    List<ExpenseDTOResponse> getAll(Pageable pageable);

    ExpenseDTOResponse post(ExpenseDTORequest expenseDTORequest) throws DuplicateExpenseException;

}
