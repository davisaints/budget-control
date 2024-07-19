package budget.control.project.controller;

import budget.control.project.dto.ExpenseDTORequest;
import budget.control.project.dto.ExpenseDTOResponse;
import budget.control.project.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "expense")
public class ExpenseController {

  @Autowired ExpenseService expenseService;

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    expenseService.delete(id);

    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<Page<ExpenseDTOResponse>> getAll(
      @PageableDefault(sort = {"date"}) String description, Pageable pageable) {
    return new ResponseEntity<>(expenseService.getAll(description, pageable), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExpenseDTOResponse> getById(@PathVariable Long id) {
    return new ResponseEntity<>(expenseService.getById(id), HttpStatus.OK);
  }

  @GetMapping("/{year}/{month}")
  public ResponseEntity<Page<ExpenseDTOResponse>> getByYearAndMonth(
      @PathVariable Integer year, @PathVariable Integer month, Pageable pageable) {
    return new ResponseEntity<>(
        expenseService.getByYearAndMonth(year, month, pageable), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<ExpenseDTOResponse> post(
      @RequestBody @Valid ExpenseDTORequest expenseDTORequest) {
    return new ResponseEntity<>(expenseService.post(expenseDTORequest), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ExpenseDTOResponse> put(
      @RequestBody @Valid ExpenseDTORequest expenseDTORequest, @PathVariable Long id) {
    return new ResponseEntity<>(expenseService.put(expenseDTORequest, id), HttpStatus.CREATED);
  }
}
