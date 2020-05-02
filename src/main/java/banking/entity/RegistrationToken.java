package banking.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RegistrationToken {

  private long id;
  private String tokenName;
  private LocalDateTime expirationDate;
  private long accountId;
}
