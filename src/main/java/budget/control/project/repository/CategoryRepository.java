package budget.control.project.repository;

import budget.control.project.model.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  Category findByName(String name);

  Optional<Category> findByNameIgnoreCase(String name);
}
