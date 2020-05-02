package banking.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CreditCard {

  private long id;
  private long account_id;
  private String cardNumber;
  private String pinCode;
  private double balance;
}
