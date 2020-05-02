package banking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferDto {
  private String cardFrom;
  private String cardTo;
  private double amount;
}
