package budget.control.project.service.impl;

import budget.control.project.dto.RevenueDTORequest;
import budget.control.project.dto.RevenueDTOResponse;
import budget.control.project.exception.DuplicateRevenueException;
import budget.control.project.model.Revenue;
import budget.control.project.repository.CategoryRepository;
import budget.control.project.repository.RevenueRepository;
import budget.control.project.service.RevenueService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RevenueServiceImpl implements RevenueService {

  @Autowired private CategoryRepository categoryRepository;
  @Autowired private RevenueRepository revenueRepository;

  @Override
  public void delete(Long id) {
    Revenue revenue =
        revenueRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Revenue not found with id: " + id));

    revenueRepository.delete(revenue);
  }

  @Override
  public Page<RevenueDTOResponse> getAll(String description, Pageable pageable) {
    if (description == null) {
      return revenueRepository.findAll(pageable).map(RevenueDTOResponse::new);
    } else {
      return revenueRepository
          .findByDescriptionContaining(description, pageable)
          .map(RevenueDTOResponse::new);
    }
  }

  @Override
  public RevenueDTOResponse getById(Long id) {
    Revenue revenue =
        revenueRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Revenue not found with id: " + id));

    return new RevenueDTOResponse(revenue);
  }

  @Override
  public Page<RevenueDTOResponse> getByYearAndMonth(
      Integer year, Integer month, Pageable pageable) {
    if (year == null || month == null) {
      throw new IllegalArgumentException("Year and month must be provided");
    }

    return revenueRepository.findByYearAndMonth(year, month, pageable).map(RevenueDTOResponse::new);
  }

  @Override
  public RevenueDTOResponse post(RevenueDTORequest revenueDTORequest) {
    if (revenueRepository.findByDescriptionAndTransactionDate(
            revenueDTORequest.getDescription(), revenueDTORequest.getTransactionDate())
        != null) {
      throw new DuplicateRevenueException(
          "Revenue with the given description and transaction date already exists");
    }

    if (revenueDTORequest.getCategoryName() == null) {
      revenueDTORequest.setCategory(categoryRepository.findByName("Other"));
    } else {
      revenueDTORequest.setCategory(
          categoryRepository.findByNameIgnoreCase(revenueDTORequest.getCategoryName()));
    }

    Revenue revenue = revenueRepository.save(new Revenue(revenueDTORequest));

    return new RevenueDTOResponse(revenue);
  }

  @Override
  public RevenueDTOResponse put(RevenueDTORequest revenueDTORequest, Long id) {
    Revenue existingRevenue =
        revenueRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Revenue not found with id: " + id));

    Revenue duplicateRevenue =
        revenueRepository.findByDescriptionAndTransactionDate(
            revenueDTORequest.getDescription(), revenueDTORequest.getTransactionDate());

    if (duplicateRevenue != null && !Objects.equals(duplicateRevenue.getId(), id)) {
      throw new DuplicateRevenueException(
          "Revenue with the given description and transaction date already exists");
    }

    if (revenueDTORequest.getCategoryName() == null) {
      revenueDTORequest.setCategory(categoryRepository.findByName("Other"));
    } else {
      revenueDTORequest.setCategory(
          categoryRepository.findByNameIgnoreCase(revenueDTORequest.getCategoryName()));
    }

    existingRevenue.update(revenueDTORequest, revenueDTORequest.getCategory());

    revenueRepository.save(existingRevenue);

    return new RevenueDTOResponse(existingRevenue);
  }
}
