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

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handleEntityNotFound(Exception ex) {
    logger.severe(ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: " + ex.getMessage());
  }
}
