package budget.control.project.service.impl;

import budget.control.project.dto.RevenueDTORequest;
import budget.control.project.dto.RevenueDTOResponse;
import budget.control.project.exception.DuplicateRevenueException;
import budget.control.project.model.Revenue;
import budget.control.project.repository.RevenueRepository;
import budget.control.project.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevenueServiceImpl implements RevenueService {
    @Autowired
    private RevenueRepository repository;

    @Override
    public RevenueDTOResponse post(RevenueDTORequest revenueDTORequest) {
        List<Revenue> revenues = repository.findByDescriptionAndDate(revenueDTORequest.getDescription(), revenueDTORequest.getDate());

        if (!revenues.isEmpty()) {
            throw new DuplicateRevenueException("Duplicate entries with an existing description and month are not allowed");
        }

        Revenue revenue = repository.save(new Revenue(revenueDTORequest));

        return new RevenueDTOResponse(revenue);
    }
}
