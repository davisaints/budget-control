package budget.control.project.service;

import budget.control.project.dto.response.MonthlySummaryDTOResponse;

public interface SummaryService {

  MonthlySummaryDTOResponse findMonthlySummary(Integer year, Integer month);
}
