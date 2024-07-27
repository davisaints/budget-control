package budget.control.project.service;

import budget.control.project.dto.PaginationDTOResponse;
import budget.control.project.dto.RevenueDTORequest;
import budget.control.project.dto.RevenueDTOResponse;
import org.springframework.data.domain.Pageable;

public interface RevenueService {

  void deleteRevenue(Long id);

  PaginationDTOResponse<RevenueDTOResponse> findAll(String description, Pageable pageable);

  RevenueDTOResponse findById(Long id);

  PaginationDTOResponse<RevenueDTOResponse> findByYearAndMonth(
      Integer year, Integer month, Pageable pageable);

  RevenueDTOResponse postRevenue(RevenueDTORequest revenueDTORequest);

  RevenueDTOResponse putRevenue(RevenueDTORequest revenueDTORequest, Long id);
}
