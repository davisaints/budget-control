package budget.control.project.service;

import budget.control.project.dto.ExpenseDTORequest;
import budget.control.project.dto.ExpenseDTOResponse;
import budget.control.project.exception.DuplicateExpenseException;

public interface ExpenseService {

    ExpenseDTOResponse post(ExpenseDTORequest expenseDTORequest) throws DuplicateExpenseException;

}
