package budget.control.project.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import budget.control.project.dto.request.RevenueDTORequest;
import budget.control.project.dto.response.PaginationDTOResponse;
import budget.control.project.dto.response.RevenueDTOResponse;
import budget.control.project.exception.DuplicateRevenueException;
import budget.control.project.model.Revenue;
import budget.control.project.repository.RevenueRepository;
import budget.control.project.service.impl.RevenueServiceImpl;
import budget.control.project.utils.BigDecimalUtil;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class RevenueServiceImplTest {

  @InjectMocks private RevenueServiceImpl revenueServiceImpl;

  @Mock private RevenueRepository revenueRepository;

  private Revenue revenue;
  private RevenueDTORequest request;

  @BeforeEach
  void setup() {
    request =
        new RevenueDTORequest(
            BigDecimalUtil.roundWithCeiling(BigDecimal.valueOf(100)),
            "Lunch",
            LocalDate.of(2020, 10, 10));

    revenue = new Revenue(request);
  }

  @AfterEach
  void tearDown() {
    revenueRepository.deleteAll();
  }

  @Test
  void givenDescription_whenFindAll_thenReturnFilteredRevenues() {
    // Arrange
    String description = "Lunch";
    Pageable pageable = PageRequest.of(0, 10);
    Page<Revenue> revenuePage = new PageImpl<>(List.of(revenue), pageable, 1);
    given(revenueRepository.findByDescriptionContaining(description, pageable))
        .willReturn(revenuePage);

    // Act
    PaginationDTOResponse<RevenueDTOResponse> result =
        revenueServiceImpl.findAll(description, pageable);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals(1, result.getTotalPages());
    assertTrue(result.isLast());
    then(revenueRepository).should().findByDescriptionContaining(description, pageable);
  }

  @Test
  void givenDuplicateRevenue_whenSaving_thenThrowDuplicateRevenueException() {
    // Arrange
    given(
            revenueRepository.findByDescriptionAndTransactionDate(
                request.getDescription(), request.getTransactionDate()))
        .willReturn(revenue);

    // Act & Assert
    DuplicateRevenueException exception =
        assertThrows(
            DuplicateRevenueException.class, () -> revenueServiceImpl.postRevenue(request));

    // Assert
    assertEquals(
        "Revenue with the given description and transaction date already exists",
        exception.getMessage());
  }

  @Test
  void givenExistingRevenue_whenUpdating_thenReturnRevenueDtoResponseWithUpdatedFields() {
    // Arrange
    RevenueDTORequest updatedRevenue =
        new RevenueDTORequest(
            BigDecimalUtil.roundWithCeiling(BigDecimal.valueOf(200)),
            "Party",
            LocalDate.of(2021, 12, 30));

    // Act
    revenue.update(updatedRevenue);

    // Assert
    assertEquals(revenue.getAmount(), updatedRevenue.getAmount());
    assertEquals(revenue.getDescription(), updatedRevenue.getDescription());
    assertEquals(revenue.getTransactionDate(), updatedRevenue.getTransactionDate());
  }

  @Test
  void givenInvalidId_whenFindById_thenThrowEntityNotFoundException() {
    // Arrange
    Long revenueId = 1L;
    given(revenueRepository.findById(revenueId)).willReturn(Optional.empty());

    // Act & Assert
    EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class,
            () -> {
              revenueServiceImpl.findById(revenueId);
            });

    assertEquals("Revenue not found with id: " + revenueId, exception.getMessage());
    then(revenueRepository).should().findById(revenueId);
  }

  @Test
  void givenNoDescription_whenFindAll_thenReturnAllRevenues() {
    // Arrange
    Pageable pageable = PageRequest.of(0, 10);
    Page<Revenue> revenuePage = new PageImpl<>(List.of(revenue), pageable, 1);
    given(revenueRepository.findAll(pageable)).willReturn(revenuePage);

    // Act
    PaginationDTOResponse<RevenueDTOResponse> result = revenueServiceImpl.findAll(null, pageable);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals(1, result.getTotalPages());
    assertTrue(result.isLast());
    then(revenueRepository).should().findAll(pageable);
  }

  @Test
  void givenNonExistingId_whenPutRevenue_thenThrowEntityNotFoundException() {
    // Arrange
    Long nonExistingId = 1L;
    given(revenueRepository.findById(nonExistingId)).willReturn(Optional.empty());

    // Act
    EntityNotFoundException exception =
        assertThrows(
            EntityNotFoundException.class,
            () -> {
              revenueServiceImpl.putRevenue(request, nonExistingId);
            });

    // Assert
    assertEquals("Revenue not found with id: " + nonExistingId, exception.getMessage());
  }

  @Test
  void givenNullYearOrMonth_whenFindByYearAndMonth_thenThrowBadRequestException() {
    // Arrange
    Pageable pageable = PageRequest.of(0, 10);

    // Act & Assert
    ResponseStatusException exception =
        assertThrows(
            ResponseStatusException.class,
            () -> revenueServiceImpl.findByYearAndMonth(null, 10, pageable));

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("Year and month must be provided", exception.getReason());

    exception =
        assertThrows(
            ResponseStatusException.class,
            () -> revenueServiceImpl.findByYearAndMonth(2020, null, pageable));

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("Year and month must be provided", exception.getReason());
  }

  @Test
  void givenSavedRevenue_whenDelete_thenRemoveFromDB() {
    // Arrange
    Long revenueId = 1L;
    given(revenueRepository.findById(revenueId)).willReturn(Optional.of(revenue));

    // Act
    revenueServiceImpl.deleteRevenue(revenueId);

    // Assert
    then(revenueRepository).should().delete(revenue);
  }

  @Test
  void givenValidYearAndMonth_whenFindByYearAndMonth_thenReturnFilteredRevenues() {
    // Arrange
    int year = 2020;
    int month = 10;
    Pageable pageable = PageRequest.of(0, 10);
    Page<Revenue> revenuePage = new PageImpl<>(List.of(revenue), pageable, 1);
    given(revenueRepository.findByYearAndMonth(year, month, pageable)).willReturn(revenuePage);

    // Act
    PaginationDTOResponse<RevenueDTOResponse> result =
        revenueServiceImpl.findByYearAndMonth(year, month, pageable);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals(1, result.getTotalPages());
    assertTrue(result.isLast());
    then(revenueRepository).should().findByYearAndMonth(year, month, pageable);
  }

  @Test
  void givenRevenue_whenFindById_thenReturnRevenueDtoResponse() {
    // Arrange
    Long revenueId = 1L;
    given(revenueRepository.findById(revenueId)).willReturn(Optional.of(revenue));

    // Act
    RevenueDTOResponse result = revenueServiceImpl.findById(revenueId);

    // Assert
    assertNotNull(result);
    assertEquals(revenue.getAmount(), result.getAmount());
    assertEquals(revenue.getDescription(), result.getDescription());
    assertEquals(revenue.getTransactionDate(), result.getTransactionDate());
    then(revenueRepository).should().findById(revenueId);
  }
}
