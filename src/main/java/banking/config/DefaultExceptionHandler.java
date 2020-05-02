package banking.config;

import banking.exception.EntityNotFoundException;
import banking.exception.TransferException;
import banking.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

  @ExceptionHandler(TransferException.class)
  public final ResponseEntity<ErrorDetails> handleUnsupportedException(Exception ex, WebRequest request) {
    log.warn("Transfer exception", ex);
    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ValidationException.class)
  public final ResponseEntity<ErrorDetails> handleValidationException(
      ValidationException ex, WebRequest request) {
    log.warn("Validation exception", ex);
    ErrorDetails errorDetails =
        new ErrorDetails(LocalDateTime.now(), ex.getErrorMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public final ResponseEntity<ErrorDetails> handleEntityNotFoundException(Exception ex, WebRequest request) {
    log.warn("Entity is not found exception", ex);
    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @Getter
  @Setter
  private static class ErrorDetails {

    private LocalDateTime timestamp;
    private String message;
    private String details;

    ErrorDetails(LocalDateTime timestamp, String message, String details) {
      this.timestamp = timestamp;
      this.message = message;
      this.details = details;
    }

    @JsonCreator
    public ErrorDetails() {
    }

  }
}
