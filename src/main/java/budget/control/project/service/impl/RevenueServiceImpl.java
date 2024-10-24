package budget.control.project.service.impl;

import budget.control.project.dto.request.RevenueDTORequest;
import budget.control.project.dto.response.PaginationDTOResponse;
import budget.control.project.dto.response.RevenueDTOResponse;
import budget.control.project.exception.DuplicateRevenueException;
import budget.control.project.model.Revenue;
import budget.control.project.repository.RevenueRepository;
import budget.control.project.service.RevenueService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RevenueServiceImpl implements RevenueService {

  private final RevenueRepository revenueRepository;

  public RevenueServiceImpl(RevenueRepository revenueRepository) {
    this.revenueRepository = revenueRepository;
  }

  @Override
  public void deleteRevenue(Long id) {
    Revenue revenue =
        revenueRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Revenue not found with id: " + id));

    revenueRepository.delete(revenue);
  }

  @Override
  public PaginationDTOResponse<RevenueDTOResponse> findAll(String description, Pageable pageable) {
    Page<RevenueDTOResponse> revenueDTOResponsePage;

    revenueDTOResponsePage =
        (description == null)
            ? revenueRepository.findAll(pageable).map(RevenueDTOResponse::new)
            : revenueRepository
                .findByDescriptionContaining(description, pageable)
                .map(RevenueDTOResponse::new);

    return new PaginationDTOResponse<RevenueDTOResponse>()
        .builder()
        .setContent(revenueDTOResponsePage.getContent())
        .setPage(revenueDTOResponsePage.getNumber() + 1)
        .setSize(revenueDTOResponsePage.getSize())
        .setTotalElements(revenueDTOResponsePage.getTotalElements())
        .setTotalPages(revenueDTOResponsePage.getTotalPages())
        .setLast(revenueDTOResponsePage.isLast())
        .build();
  }

  @Override
  public RevenueDTOResponse findById(Long id) {
    Revenue revenue =
        revenueRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Revenue not found with id: " + id));

    return new RevenueDTOResponse(revenue);
  }

  @Override
  public PaginationDTOResponse<RevenueDTOResponse> findByYearAndMonth(
      Integer year, Integer month, Pageable pageable) {
    if (year == null || month == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Year and month must be provided");
    }

    Page<RevenueDTOResponse> revenueDTOResponsePage =
        revenueRepository.findByYearAndMonth(year, month, pageable).map(RevenueDTOResponse::new);

    return new PaginationDTOResponse<RevenueDTOResponse>()
        .builder()
        .setContent(revenueDTOResponsePage.getContent())
        .setPage(revenueDTOResponsePage.getNumber() + 1)
        .setSize(revenueDTOResponsePage.getSize())
        .setTotalElements(revenueDTOResponsePage.getTotalElements())
        .setTotalPages(revenueDTOResponsePage.getTotalPages())
        .setLast(revenueDTOResponsePage.isLast())
        .build();
  }

  @Override
  public RevenueDTOResponse postRevenue(RevenueDTORequest revenueDTORequest) {
    if (revenueRepository.findByDescriptionAndTransactionDate(
            revenueDTORequest.getDescription(), revenueDTORequest.getTransactionDate())
        != null) {
      throw new DuplicateRevenueException(
          "Revenue with the given description and transaction date already exists");
    }

    Revenue revenue = revenueRepository.save(new Revenue(revenueDTORequest));

    return new RevenueDTOResponse(revenue);
  }

  @Override
  public RevenueDTOResponse putRevenue(RevenueDTORequest revenueDTORequest, Long id) {
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

    existingRevenue.update(revenueDTORequest);

    revenueRepository.save(existingRevenue);

    return new RevenueDTOResponse(existingRevenue);
  }
}
