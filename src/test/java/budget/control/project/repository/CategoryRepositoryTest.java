package budget.control.project.repository;

import static org.assertj.core.api.Assertions.assertThat;

import budget.control.project.model.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class CategoryRepositoryTest {

  @Autowired private CategoryRepository categoryRepository;

  @BeforeEach
  void setUp() {
    if (categoryRepository.findByName("Food") == null) {
      categoryRepository.save(new Category("Food"));
    }
  }

  @AfterEach
  void tearDown() {
    categoryRepository.deleteAll();
  }

  @Test
  @DisplayName("Should find a Category by its name")
  void shouldFindCategoryByName() {
    assertThat(categoryRepository.findByName("Food").getName()).isEqualTo("Food");
  }

  @Test
  @DisplayName("Should find a Category by its name ignoring case")
  void shouldFindByNameIgnoreCase() {
    assertThat(categoryRepository.findByNameIgnoreCase("FOOD").get().getName()).isEqualTo("Food");
  }
}
