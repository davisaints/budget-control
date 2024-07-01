package budget.control.project.controller;

import budget.control.project.dto.ExpenseDTORequest;
import budget.control.project.dto.ExpenseDTOResponse;
import budget.control.project.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "expenses")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<ExpenseDTOResponse>> getAll(@PageableDefault(size = 10, sort = {"date"}) Pageable pageable) {
        return new ResponseEntity<>(expenseService.getAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExpenseDTOResponse> post(@RequestBody @Valid ExpenseDTORequest expenseDTORequest) {
        return new ResponseEntity<>(expenseService.post(expenseDTORequest), HttpStatus.CREATED);
    }

}
