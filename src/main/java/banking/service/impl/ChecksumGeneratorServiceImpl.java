package banking.service.impl;

import banking.service.ChecksumGeneratorService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ChecksumGeneratorServiceImpl implements ChecksumGeneratorService {

  @Override
  public int generateChecksum(char[] digits) {
    int[] copyDigits = new int[digits.length];

    for (int i = 0; i < digits.length; i++) {
      copyDigits[i] = Integer.parseInt(String.valueOf(digits[i]));
    }

    for (int i = copyDigits.length - 1; i >= 0; i -= 2) {
      int digit = copyDigits[i];

      digit = digit * 2;
      if (digit > 9) {
        digit = (digit % 10) + 1;
      }
      copyDigits[i] = digit;
    }

    int sum = Arrays.stream(copyDigits).sum();
    int mod = sum % 10;

    return mod == 0 ? 0 : 10 - mod;
  }

  @Override
  public boolean verifyCreditCard(String creditCardNumber) {
    String lastDigit = creditCardNumber.substring(creditCardNumber.length() - 1);
    return Integer.parseInt(lastDigit) == generateChecksum(creditCardNumber.toCharArray());
  }
}
