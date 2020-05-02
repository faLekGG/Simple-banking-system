package banking.service.impl.security;

import banking.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationSenderImpl implements RegistrationSender {

  private final RegistrationTokenService registrationTokenService;
  private final JavaMailSender mailSender;

  @Override
  public void sendAnApprovalForRegistration(Account account) {
    String token = UUID.randomUUID().toString();
    registrationTokenService.createRegistrationToken(account.getUsername(), token);

    String subject = "Registration Confirmation";
    String confirmationUrl
        = "/registrationConfirm?token=" + token;

    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo("vhalaveika@varteq.com");
    email.setSubject(subject);
    email.setText("Confirmation letter" + "\r\n" + "http://localhost:8080" + confirmationUrl);
    mailSender.send(email);
  }
}
