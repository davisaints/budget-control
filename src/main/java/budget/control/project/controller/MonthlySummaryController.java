package budget.control.project.controller;

import budget.control.project.dto.MonthlySummaryDTOResponse;
import budget.control.project.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "summary")
public class MonthlySummaryController {

  @Autowired SummaryService summaryService;

  @GetMapping("/{year}/{month}")
  public ResponseEntity<MonthlySummaryDTOResponse> findMonthlyExpenseSummary(
      @PathVariable Integer year, @PathVariable Integer month) {
    return new ResponseEntity<>(summaryService.findSummary(year, month), HttpStatus.OK);
  }
}
