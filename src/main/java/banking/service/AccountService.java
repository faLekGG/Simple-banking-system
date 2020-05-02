package banking.service;

import banking.dto.AccountDto;
import banking.exception.EntityNotFoundException;
import banking.exception.RegistrationConfirmationException;

public interface AccountService {

  void createNewAccount(AccountDto accountDto);

  void confirmRegistration(String token) throws EntityNotFoundException, RegistrationConfirmationException;

  AccountDto findAccount(String userName) throws EntityNotFoundException;
}
