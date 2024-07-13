package budget.control.project.service.impl;

import budget.control.project.dto.RevenueDTORequest;
import budget.control.project.dto.RevenueDTOResponse;
import budget.control.project.exception.DuplicateRevenueException;
import budget.control.project.model.Revenue;
import budget.control.project.repository.CategoryRepository;
import budget.control.project.repository.RevenueRepository;
import budget.control.project.service.RevenueService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
  public Page<RevenueDTOResponse> getAll(Pageable pageable) {
    return revenueRepository.findAll(pageable).map(RevenueDTOResponse::new);
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
  public RevenueDTOResponse post(RevenueDTORequest revenueDTORequest) {
    if (revenueRepository.findByDescriptionAndDate(
            revenueDTORequest.getDescription(), revenueDTORequest.getDate())
        != null) {
      throw new DuplicateRevenueException(
          "Duplicate entries with an existing description and month are not allowed");
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
    Revenue revenue =
        revenueRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Revenue not found with id: " + id));

    if (revenueRepository.findByDescriptionAndDate(
            revenueDTORequest.getDescription(), revenueDTORequest.getDate())
        != null) {
      throw new DuplicateRevenueException(
          "Duplicate entries with an existing description and month are not allowed");
    }

    if (revenueDTORequest.getCategoryName() == null) {
      revenueDTORequest.setCategory(categoryRepository.findByName("Other"));
    } else {
      revenueDTORequest.setCategory(
          categoryRepository.findByNameIgnoreCase(revenueDTORequest.getCategoryName()));
    }

    revenue.update(revenueDTORequest, revenueDTORequest.getCategory());

    revenueRepository.save(revenue);

    return new RevenueDTOResponse(revenue);
  }
}
