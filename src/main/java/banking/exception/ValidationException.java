package banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends Exception {

  public static final long serialVersionUID = 1L;

  private final Errors errors;

  public ValidationException(Errors errors) {
    this.errors = errors;
  }

  /**
   * Return result message string of validation errors.
   *
   * @return String
   */
  public String getErrorMessage() {
    return errors.getAllErrors().stream()
        .map(this::getErrorMessage)
        .collect(Collectors.joining("\n"));
  }

  private String getErrorMessage(ObjectError error) {
    return error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage();
  }
}
