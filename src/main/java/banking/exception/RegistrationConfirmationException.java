package banking.exception;

public class RegistrationConfirmationException extends Exception {

  public static final long serialVersionUID = 1L;

  public RegistrationConfirmationException(String message) {
    super(message);
  }

  public RegistrationConfirmationException(String message, Throwable cause) {
    super(message, cause);
  }
}
