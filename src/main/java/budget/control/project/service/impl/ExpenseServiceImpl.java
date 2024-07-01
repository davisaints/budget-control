package budget.control.project.service.impl;

import budget.control.project.dto.ExpenseDTORequest;
import budget.control.project.dto.ExpenseDTOResponse;
import budget.control.project.exception.DuplicateRevenueException;
import budget.control.project.model.Expense;
import budget.control.project.repository.ExpenseRepository;
import budget.control.project.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ExpenseRepository repository;

    @Override
    public List<ExpenseDTOResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).stream()
                .map(ExpenseDTOResponse::new)
                .toList();
    }

    @Override
    public ExpenseDTOResponse post(ExpenseDTORequest expenseDTORequest) {
        if (repository.findByDescriptionAndDate(expenseDTORequest.getDescription(), expenseDTORequest.getDate()) != null) {
            throw new DuplicateRevenueException("Duplicate entries with an existing description and month are not allowed");
        }

        Expense expense = repository.save(new Expense(expenseDTORequest));

        return new ExpenseDTOResponse(expense);
    }

}
