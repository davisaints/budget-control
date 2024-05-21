package budget.control.project.service;

import budget.control.project.dto.RevenueDTORequest;
import budget.control.project.dto.RevenueDTOResponse;

public interface RevenueService {
    RevenueDTOResponse post(RevenueDTORequest revenueDTORequest);
}
