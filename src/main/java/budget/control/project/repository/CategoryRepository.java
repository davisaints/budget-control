package budget.control.project.repository;

import budget.control.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  Category findByName(String name);

  Category findByNameIgnoreCase(String name);
}
