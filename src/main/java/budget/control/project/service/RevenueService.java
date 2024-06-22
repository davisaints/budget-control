package budget.control.project.service;

import budget.control.project.dto.RevenueDTORequest;
import budget.control.project.dto.RevenueDTOResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RevenueService {
    RevenueDTOResponse post(RevenueDTORequest revenueDTORequest);

    List<RevenueDTOResponse> getAll(Pageable pageable);

    RevenueDTOResponse put(RevenueDTORequest revenueDTORequest, Long id);

    void delete(Long id);
}
