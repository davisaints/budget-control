package budget.control.project.service;

import budget.control.project.dto.MonthlySummaryDTOResponse;

public interface SummaryService {

  MonthlySummaryDTOResponse findMonthlySummary(Integer year, Integer month);
}
