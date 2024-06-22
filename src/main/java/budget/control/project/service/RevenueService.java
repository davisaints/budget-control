package budget.control.project.service;

import budget.control.project.dto.RevenueDTORequest;
import budget.control.project.dto.RevenueDTOResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RevenueService {

    void delete(Long id);

    List<RevenueDTOResponse> getAll(Pageable pageable);

    RevenueDTOResponse post(RevenueDTORequest revenueDTORequest);

    RevenueDTOResponse put(RevenueDTORequest revenueDTORequest, Long id);

}
