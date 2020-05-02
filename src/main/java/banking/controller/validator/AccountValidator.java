package banking.controller.validator;

import banking.dto.AccountDto;
import banking.exception.EntityNotFoundException;
import banking.exception.ValidationException;
import banking.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountValidator implements Validator {

  private final AccountService accountService;

  @Override
  public boolean supports(Class<?> clazz) {
    return AccountDto.class.equals(clazz);
  }

  @SneakyThrows
  @Override
  public void validate(Object target, Errors errors) {
    AccountDto account = (AccountDto) target;

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "notEmpty");

    if (account.getUserName().length() < 6 || account.getUserName().length() > 32) {
      errors.rejectValue("userName", "size.username");
    }

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "notEmpty");

    if (account.getEmail().length() < 6 || account.getEmail().length() > 32) {
      errors.rejectValue("email", "size.email");
    }

    try {
      if (accountService.findAccount(account.getUserName()) != null) {
        errors.rejectValue("userName", "duplicate.username");
      }
    } catch (EntityNotFoundException e) {
      log.error(String.format("Account with userName %s was not found", account.getUserName()));
    }

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "notEmpty");

    if (account.getPassword().length() < 8 || account.getPassword().length() > 32) {
      errors.rejectValue("password", "size.password");
    }

    if (errors.hasErrors()) {
      throw new ValidationException(errors);
    }

   /* if (!user.getPasswordConfirm().equals(user.getPassword())) {
      errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
    }*/
  }
}
