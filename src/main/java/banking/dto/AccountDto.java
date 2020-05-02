package banking.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AccountDto {
  private String userName;
  private String password;
  private String email;
  private LocalDateTime createdOn;
  private LocalDateTime lastLogin;
}
