package budget.control.project.controller;

import budget.control.project.dto.response.MonthlySummaryDTOResponse;
import budget.control.project.service.SummaryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "summary")
@SecurityRequirement(name = "bearer-key")
public class MonthlySummaryController {

  @Autowired SummaryService summaryService;

  @GetMapping("/{year}/{month}")
  public ResponseEntity<MonthlySummaryDTOResponse> findMonthlySummary(
      @PathVariable Integer year, @PathVariable Integer month) {
    return new ResponseEntity<>(summaryService.findMonthlySummary(year, month), HttpStatus.OK);
  }
}
