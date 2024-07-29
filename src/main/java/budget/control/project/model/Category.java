package budget.control.project.model;

import jakarta.persistence.*;

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

  public String getName() {
    return name;
  }
}
