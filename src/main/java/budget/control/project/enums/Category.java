package budget.control.project.enums;

public enum Category {
  EDUCATION("Education"),
  FOOD("Food"),
  HEALTH("Health"),
  RENT("Rent"),
  OTHER("Other"),
  RECREATION("Recreation"),
  TRANSPORTATION("Transportation"),
  UNEXPECTED_EXPENSE("Unexpected expense");

  public final String categoryName;

  private Category(String categoryName) {
    this.categoryName = categoryName;
  }

  public static Category findByName(String name) {
    for (Category category : values()) {
      if (category.name().equalsIgnoreCase(name)) {
        return category;
      }
    }
    throw new IllegalArgumentException(
        "Invalid category. Available categories: Education, Food, Health, Rent, Other, Recreation, Transportation, or Unexpected Expense.");
  }
}
