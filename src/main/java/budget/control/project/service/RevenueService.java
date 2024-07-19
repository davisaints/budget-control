package budget.control.project.service;

import budget.control.project.dto.RevenueDTORequest;
import budget.control.project.dto.RevenueDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RevenueService {

  void delete(Long id);

  RevenueDTOResponse getById(Long id);

  Page<RevenueDTOResponse> getAll(String description, Pageable pageable);

  Page<RevenueDTOResponse> getByYearAndMonth(Integer year, Integer month, Pageable pageable);

  RevenueDTOResponse post(RevenueDTORequest revenueDTORequest);

  RevenueDTOResponse put(RevenueDTORequest revenueDTORequest, Long id);
}
