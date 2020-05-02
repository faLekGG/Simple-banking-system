package banking.exception;

public class EntityNotFoundException extends Exception {

  public static final long serialVersionUID = 1L;

  public EntityNotFoundException(String message) {
    super(message);
  }

  public EntityNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
