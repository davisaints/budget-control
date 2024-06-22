package budget.control.project.service.impl;

import budget.control.project.dto.RevenueDTORequest;
import budget.control.project.dto.RevenueDTOResponse;
import budget.control.project.exception.DuplicateRevenueException;
import budget.control.project.model.Revenue;
import budget.control.project.repository.RevenueRepository;
import budget.control.project.service.RevenueService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevenueServiceImpl implements RevenueService {
    @Autowired
    private RevenueRepository repository;

    @Override
    public RevenueDTOResponse post(RevenueDTORequest revenueDTORequest) {
        if (repository.findByDescriptionAndDate(revenueDTORequest.getDescription(), revenueDTORequest.getDate()) != null) {
            throw new DuplicateRevenueException("Duplicate entries with an existing description and month are not allowed");
        }

        Revenue revenue = repository.save(new Revenue(revenueDTORequest));

        return new RevenueDTOResponse(revenue);
    }

    @Override
    public List<RevenueDTOResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).stream()
                .map(RevenueDTOResponse::new)
                .toList();
    }

    @Override
    public RevenueDTOResponse put(RevenueDTORequest revenueDTORequest, Long id) {
        Revenue revenue = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Revenue not found with id: " + id));

        revenue.setDescription(revenueDTORequest.getDescription());
        revenue.setAmount(revenueDTORequest.getAmount());
        revenue.setDate(revenueDTORequest.getDate());

        repository.save(revenue);

        return new RevenueDTOResponse(revenue);
    }

    @Override
    public void delete(Long id) {
        Revenue revenue = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Revenue not found with id: " + id));

        repository.delete(revenue);
    }
}