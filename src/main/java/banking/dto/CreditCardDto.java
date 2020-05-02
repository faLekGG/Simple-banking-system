package banking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CreditCardDto {
  private double amount;
  private String cardNumber;
}
