package banking.service.impl;

import banking.dao.AccountDao;
import banking.dao.CreditCardDao;
import banking.dto.BankIssuedCreditCard;
import banking.dto.CreditCardDto;
import banking.entity.Account;
import banking.entity.CreditCard;
import banking.service.CreditCardGeneratorService;
import banking.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

  private final CreditCardGeneratorService creditCardGeneratorService;
  private final CreditCardDao<CreditCard> creditCardDao;
  private final AccountDao<Account> accountDao;
  private final ModelMapper modelMapper = new ModelMapper();

  @Override
  public CreditCardDto openNewCard(String userName) {
    log.debug(">>open new card");
    String creditNumber = creditCardGeneratorService.generateCardNumber();
    String pinCode = creditCardGeneratorService.generatePinCode();

    CreditCard creditCard = new CreditCard();
    creditCard.setCardNumber(creditNumber);
    creditCard.setPinCode(pinCode);
    int accountId = accountDao.findAccountIdByUserName(userName);
    creditCardDao.insertCreditCardData(creditCard, accountId);

    log.debug("\nYour card have been created");
    log.debug("Your card number:\n" + creditNumber);
    log.debug("<< open new card");

    CreditCardDto creditCardDto = new BankIssuedCreditCard();
    modelMapper.map(creditCard, creditCardDto);

    return creditCardDto;
  }

  public CreditCardDto findCreditCard(String cardNumber) {
    CreditCardDto creditCardDto = new BankIssuedCreditCard();
    modelMapper.map(creditCardDao.findCreditCard(cardNumber), creditCardDto);
    return creditCardDto;
  }

  @Override
  public void addIncome(String cardNumber, double income) {
    creditCardDao.updateBalance(cardNumber, income);
  }

  @Override
  public CreditCardDto getCreditCardBalance(String cardNumber) {
    CreditCardDto creditCardDto = new BankIssuedCreditCard();
    creditCardDto.setAmount(creditCardDao.getCreditCardBalance(cardNumber));
    return creditCardDto;
  }

  @Override
  public void closeCreditCard(String cardNumber) {
    creditCardDao.deleteCard(cardNumber);
  }
}
