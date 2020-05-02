package banking.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Account {

  private long id;
  private String username;
  private String email;
  private String password;
  private boolean enabled;
  private LocalDateTime createdOn;
  private LocalDateTime lastLogin;
}
