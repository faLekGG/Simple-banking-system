package banking.service.impl.security;

import banking.entity.RegistrationToken;

import java.time.LocalDateTime;

public interface RegistrationTokenService {

  void createRegistrationToken(String userName, String token);

  RegistrationToken getRegistrationToken(String token);

  boolean isTimeNotExpired(LocalDateTime expirationTime);
}
