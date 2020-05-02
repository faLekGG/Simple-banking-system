package banking.controller;

import banking.dto.BankIssuedCreditCard;
import banking.dto.CreditCardDto;
import banking.dto.TransferDto;
import banking.exception.TransferException;
import banking.service.CreditCardService;
import banking.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

  private final CreditCardService creditCardService;
  private final TransferService transferService;


  @PostMapping("/create/{username}")
  public ResponseEntity<CreditCardDto> createNewCard(@PathVariable("username") String userName) {
    return ResponseEntity.ok(creditCardService.openNewCard(userName));
  }

  @GetMapping("/{cardNumber}")
  public ResponseEntity<CreditCardDto> getCreditCard(@PathVariable String cardNumber) {
    return ResponseEntity.ok(creditCardService.findCreditCard(cardNumber));
  }

  @DeleteMapping("/delete/{cardNumber}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteCard(@PathVariable String cardNumber) {
    creditCardService.closeCreditCard(cardNumber);
  }

  @GetMapping("/balance/{cardNumber}")
  public ResponseEntity<CreditCardDto> getCreditCardBalance(@PathVariable String cardNumber) {
    return ResponseEntity.ok(creditCardService.getCreditCardBalance(cardNumber));
  }

  @PutMapping("/income/{cardNumber}")
  @ResponseStatus(HttpStatus.OK)
  public void updateCreditCardBalance(@PathVariable String cardNumber, @RequestBody BankIssuedCreditCard creditCardDto) {
    creditCardService.addIncome(cardNumber, creditCardDto.getAmount());
  }

  @PutMapping("/transfer")
  @ResponseStatus(HttpStatus.OK)
  public void doTransfer(@RequestBody TransferDto transferDto) throws TransferException {
    transferService.doTransfer(transferDto);
  }

}
