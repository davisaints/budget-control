package budget.control.project.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "category")
public class Category {

  @Column(nullable = false, unique = true)
  private String name;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public Category(String name, Long id) {
    this.name = name;
    this.id = id;
  }

  public Category() {}

  public Category(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Category category = (Category) o;

    return Objects.equals(id, category.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public String getName() {
    return name;
  }
}
