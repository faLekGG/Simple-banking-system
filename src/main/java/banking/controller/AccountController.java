package banking.controller;

import banking.controller.validator.AccountValidator;
import banking.dto.AccountDto;
import banking.dto.LoginDto;
import banking.exception.EntityNotFoundException;
import banking.exception.RegistrationConfirmationException;
import banking.service.AccountService;
import banking.service.impl.security.RegistrationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;
  private final AccountValidator accountValidator;

  @InitBinder("accountDto")
  public void bindOrderFilterDtoValidator(WebDataBinder binder) {
    binder.addValidators(accountValidator);
  }

  @PostMapping("/create")
  public void createNewUserAccount(@RequestBody @Validated AccountDto accountDto) {
    accountService.createNewAccount(accountDto);
  }

  @GetMapping("/registrationConfirm")
  public void confirmRegistration(@RequestParam("token") String token)
      throws RegistrationConfirmationException, EntityNotFoundException {
    accountService.confirmRegistration(token);
  }

  @GetMapping("/find/{username}")
  public ResponseEntity<AccountDto> getAccount(@PathVariable("username") String userName)
      throws EntityNotFoundException {
    return ResponseEntity.ok(accountService.findAccount(userName));
  }
}
