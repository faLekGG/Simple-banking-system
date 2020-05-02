package banking.service.impl;

import banking.service.ChecksumGeneratorService;
import banking.service.CreditCardGeneratorService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CreditCardGeneratorServiceImpl implements CreditCardGeneratorService {

  // Issuer Identification Number (IIN) =
  // MII(Major Industry Identifier + Bank Identification Number)
  private static final String IIN = "400000";
  private static final int CARD_DIGITS = 9;
  private static final int PIN_DIGITS = 4;

  private final ChecksumGeneratorService checksumGeneratorService;

  public CreditCardGeneratorServiceImpl(ChecksumGeneratorService checksumGeneratorService) {
    this.checksumGeneratorService = checksumGeneratorService;
  }

  @Override
  public String generateCardNumber() {
    String mainPartOfCreditCard = IIN + generateAccountIdentifier(CARD_DIGITS);
    return mainPartOfCreditCard + checksumGeneratorService
        .generateChecksum((mainPartOfCreditCard).toCharArray());
  }

  @Override
  public String generatePinCode() {
    return generateAccountIdentifier(PIN_DIGITS);
  }

  private String generateAccountIdentifier(int bound) {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < bound; i++) {
      sb.append(random.nextInt(10));
    }

    return sb.toString();
  }
}
