package banking.service.impl;

import banking.dao.AccountDao;
import banking.dao.RoleDao;
import banking.dto.AccountDto;
import banking.entity.Account;
import banking.entity.RegistrationToken;
import banking.entity.Role;
import banking.exception.RegistrationConfirmationException;
import banking.service.AccountService;
import banking.service.impl.security.RegistrationSender;
import banking.service.impl.security.RegistrationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

  private final AccountDao<Account> accountDao;
  private final RoleDao<Role> roleDao;
  private final RegistrationSender registrationSender;
  private final RegistrationTokenService registrationTokenService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final ModelMapper modelMapper = new ModelMapper();

  @Override
  public void createNewAccount(AccountDto accountDto) {
    Account account = new Account();
    modelMapper.map(accountDto, account);
    account.setCreatedOn(LocalDateTime.now());
    account.setPassword(bCryptPasswordEncoder.encode(accountDto.getPassword()));
    accountDao.insertData(account);
    setAccountRole(account.getUsername());
    registrationSender.sendAnApprovalForRegistration(account);
  }

  @Override
  public void confirmRegistration(String token) throws RegistrationConfirmationException {
    RegistrationToken registrationToken = registrationTokenService.getRegistrationToken(token);
    if (registrationToken == null) {
      throw new RegistrationConfirmationException("registration token is not found");
    }

    if (!registrationTokenService.isTimeNotExpired(registrationToken.getExpirationDate())) {
      throw new RegistrationConfirmationException("registration token has expired");
    }
    accountDao.enableAccountById(registrationToken.getAccountId());
  }

  @Override
  public AccountDto findAccount(String userName) {
    Account account = accountDao.findAccountByUserName(userName);
    AccountDto accountDto = null;
    if (account != null) {
      accountDto = new AccountDto();
      modelMapper.map(account, accountDto);
    }
    return accountDto;
  }

  private void setAccountRole(String userName) {
    long roleId = roleDao.findDefaultRole().getId();
    long accountId = accountDao.findAccountIdByUserName(userName);
    roleDao.insertAccountRoles(accountId, roleId);
  }
}
