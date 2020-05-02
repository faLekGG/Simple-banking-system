package banking.service;

public interface ChecksumGeneratorService {

  /**
   *
   * @param digits - generated cardNumber
   * @return checksum calculated by Luhn algorithm
   */
  int generateChecksum(char[] digits);

  /**
   *
   * @param creditCardNumber - card to verify
   * @return check is card valid for an operation
   */
  boolean verifyCreditCard(String creditCardNumber);
}
