package budget.control.project.controller;

import budget.control.project.dto.request.ExpenseDTORequest;
import budget.control.project.dto.response.ExpenseDTOResponse;
import budget.control.project.dto.response.PaginationDTOResponse;
import budget.control.project.service.ExpenseService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "expense")
@SecurityRequirement(name = "bearer-key")
public class ExpenseController {

  private final ExpenseService expenseService;

  public ExpenseController(ExpenseService expenseService) {
    this.expenseService = expenseService;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
    expenseService.deleteExpense(id);

    return ResponseEntity.ok().build();
  }

  @PageableAsQueryParam
  @GetMapping
  public ResponseEntity<PaginationDTOResponse<ExpenseDTOResponse>> findAllExpenses(
      @Nullable String description, @Parameter(hidden = true) Pageable pageable) {
    return new ResponseEntity<>(expenseService.findAll(description, pageable), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExpenseDTOResponse> findExpenseById(@PathVariable Long id) {
    return new ResponseEntity<>(expenseService.findById(id), HttpStatus.OK);
  }

  @PageableAsQueryParam
  @GetMapping("/{year}/{month}")
  public ResponseEntity<PaginationDTOResponse<ExpenseDTOResponse>> findExpenseByYearAndMonth(
      @PathVariable Integer year,
      @PathVariable Integer month,
      @Parameter(hidden = true) Pageable pageable) {
    return new ResponseEntity<>(
        expenseService.findByYearAndMonth(year, month, pageable), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<ExpenseDTOResponse> postExpense(
      @RequestBody @Valid ExpenseDTORequest expenseDTORequest) {
    return new ResponseEntity<>(expenseService.postExpense(expenseDTORequest), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ExpenseDTOResponse> putExpense(
      @RequestBody @Valid ExpenseDTORequest expenseDTORequest, @PathVariable Long id) {
    return new ResponseEntity<>(
        expenseService.putExpense(expenseDTORequest, id), HttpStatus.CREATED);
  }
}
