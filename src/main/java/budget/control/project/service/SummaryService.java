package budget.control.project.service;

import budget.control.project.dto.MonthlySummaryDTOResponse;

public interface SummaryService {

  MonthlySummaryDTOResponse findSummary(Integer year, Integer month);
}
