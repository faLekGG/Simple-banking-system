package banking.exception;

public class TransferException extends Exception {

  public static final long serialVersionUID = 1L;

  public TransferException(String message) {
    super(message);
  }

  public TransferException(String message, Throwable cause) {
    super(message, cause);
  }
}
