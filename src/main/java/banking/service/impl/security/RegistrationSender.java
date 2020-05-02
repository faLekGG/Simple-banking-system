package banking.service.impl.security;

import banking.entity.Account;

public interface RegistrationSender {

  void sendAnApprovalForRegistration(Account account);
}
