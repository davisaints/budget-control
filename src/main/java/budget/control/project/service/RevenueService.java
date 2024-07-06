package budget.control.project.service;

import budget.control.project.dto.RevenueDTORequest;
import budget.control.project.dto.RevenueDTOResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface RevenueService {

  void delete(Long id);

  RevenueDTOResponse getById(Long id);

  List<RevenueDTOResponse> getAll(Pageable pageable);

  RevenueDTOResponse post(RevenueDTORequest revenueDTORequest);

  RevenueDTOResponse put(RevenueDTORequest revenueDTORequest, Long id);
}
