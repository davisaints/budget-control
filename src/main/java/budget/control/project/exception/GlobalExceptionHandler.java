package budget.control.project.exception;

import jakarta.persistence.EntityNotFoundException;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

  @ExceptionHandler(DuplicateExpenseException.class)
  public ResponseEntity<String> handleDuplicateExpenseException(DuplicateExpenseException ex) {
    logger.severe(ex.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR: " + ex.getMessage());
  }

  @ExceptionHandler(DuplicateRevenueException.class)
  public ResponseEntity<String> handleDuplicateRevenueException(DuplicateRevenueException ex) {
    logger.severe(ex.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR: " + ex.getMessage());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
    logger.severe(ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: " + ex.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
    logger.severe(ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: " + ex.getMessage());
  }

  @ExceptionHandler(InvalidCategoryException.class)
  public ResponseEntity<String> handleInvalidCategoryException(InvalidCategoryException ex) {
    logger.severe(ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: " + ex.getMessage());
  }
}
