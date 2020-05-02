package banking.service.impl.security;

import banking.dao.AccountDao;
import banking.dao.RegistrationTokenDao;
import banking.entity.Account;
import banking.entity.RegistrationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistrationTokenServiceImpl implements RegistrationTokenService {

  private final RegistrationTokenDao<RegistrationToken> registrationTokenDao;
  private final AccountDao<Account> accountDao;

  @Override
  public void createRegistrationToken(String userName, String token) {
    RegistrationToken registrationToken = new RegistrationToken();
    long accountId = accountDao.findAccountIdByUserName(userName);
    registrationToken.setAccountId(accountId);
    registrationToken.setExpirationDate(calculateExpirationDate());
    registrationToken.setTokenName(token);
    registrationTokenDao.insertTokenData(registrationToken);
  }

  @Override
  public RegistrationToken getRegistrationToken(String token) {
    return registrationTokenDao.getTokenByAccountId(token);
  }

  @Override
  public boolean isTimeNotExpired(LocalDateTime expirationTime) {
    return LocalDateTime.now().isBefore(expirationTime);
  }

  private LocalDateTime calculateExpirationDate() {
    return LocalDateTime.now().plusHours(24);
  }
}
