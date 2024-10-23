package budget.control.project.controller;

import budget.control.project.dto.request.RevenueDTORequest;
import budget.control.project.dto.response.PaginationDTOResponse;
import budget.control.project.dto.response.RevenueDTOResponse;
import budget.control.project.service.RevenueService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "revenue")
@SecurityRequirement(name = "bearer-key")
public class RevenueController {

  @Autowired RevenueService revenueService;

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteRevenue(@PathVariable Long id) {
    revenueService.deleteRevenue(id);

    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<PaginationDTOResponse<RevenueDTOResponse>> findAllRevenues(
      @PageableDefault(sort = {"date"}) String description, Pageable pageable) {
    return new ResponseEntity<>(revenueService.findAll(description, pageable), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<RevenueDTOResponse> findRevenueById(@PathVariable Long id) {
    return new ResponseEntity<>(revenueService.findById(id), HttpStatus.OK);
  }

  @GetMapping("/{year}/{month}")
  public ResponseEntity<PaginationDTOResponse<RevenueDTOResponse>> findRevenueByYearAndMonth(
      @PathVariable Integer year, @PathVariable Integer month, Pageable pageable) {
    return new ResponseEntity<>(
        revenueService.findByYearAndMonth(year, month, pageable), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<RevenueDTOResponse> postRevenue(
      @RequestBody @Valid RevenueDTORequest revenueDTORequest) {
    return new ResponseEntity<>(revenueService.postRevenue(revenueDTORequest), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<RevenueDTOResponse> putRevenue(
      @RequestBody @Valid RevenueDTORequest revenueDTORequest, @PathVariable Long id) {
    return new ResponseEntity<>(
        revenueService.putRevenue(revenueDTORequest, id), HttpStatus.CREATED);
  }
}
