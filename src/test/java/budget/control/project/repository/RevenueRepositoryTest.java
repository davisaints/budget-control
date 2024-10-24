package budget.control.project.repository;

import static org.assertj.core.api.Assertions.assertThat;

import budget.control.project.model.Revenue;
import budget.control.project.utils.BigDecimalUtil;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class RevenueRepositoryTest {

  private final RevenueRepository revenueRepository;

  public RevenueRepositoryTest(RevenueRepository revenueRepository) {
    this.revenueRepository = revenueRepository;
  }

  @BeforeEach
  void setUp() {
    revenueRepository.save(
        new Revenue(1L, BigDecimal.valueOf(50), "Dental check-up", LocalDate.of(2020, 10, 30)));
  }

  @AfterEach
  void tearDown() {
    revenueRepository.deleteAll();
  }

  @Test
  void findByYearAndMonth() {
    revenueRepository.save(
        new Revenue(2L, BigDecimal.valueOf(100), "Birthday dinner", LocalDate.of(2020, 10, 30)));

    Pageable pageable = PageRequest.of(0, 2);

    Page<Revenue> revenuePage = revenueRepository.findByYearAndMonth(2020, 10, pageable);

    assertThat(revenuePage)
        .extracting(Revenue::getDescription)
        .containsExactlyInAnyOrder("Dental check-up", "Birthday dinner");
  }

  @Test
  void findByDescriptionAndTransactionDate() {
    Revenue revenue =
        revenueRepository.findByDescriptionAndTransactionDate(
            "Dental check-up", LocalDate.of(2020, 10, 30));

    Revenue revenueWithIgnoreCase =
        revenueRepository.findByDescriptionAndTransactionDate(
            "dental check-up", LocalDate.of(2020, 10, 30));

    assertThat(revenue).isNotNull();
    assertThat(revenueWithIgnoreCase).isNotNull();
  }

  @Test
  void findByDescriptionContaining() {
    revenueRepository.save(
        new Revenue(2L, BigDecimal.valueOf(100), "Medical check-up", LocalDate.of(2020, 10, 30)));

    Pageable pageable = PageRequest.of(0, 2);

    Page<Revenue> revenuePage = revenueRepository.findByDescriptionContaining("check-up", pageable);

    assertThat(revenuePage).hasSize(2);
  }

  @Test
  void findTotalMonthlyRevenue() {
    revenueRepository.save(
        new Revenue(1L, BigDecimal.valueOf(50), "Birthday dinner", LocalDate.of(2020, 10, 30)));

    BigDecimal expectedSum = BigDecimal.valueOf(100);

    BigDecimal monthlyRevenue = revenueRepository.findTotalMonthlyRevenue(2020, 10);

    assertThat(BigDecimalUtil.roundWithCeiling(monthlyRevenue))
        .isEqualTo(BigDecimalUtil.roundWithCeiling(expectedSum));
  }
}
