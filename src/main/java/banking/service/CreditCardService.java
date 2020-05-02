package banking.service;

import banking.dto.CreditCardDto;

public interface CreditCardService {

  CreditCardDto openNewCard(String userName);

  CreditCardDto findCreditCard(String cardNumber);

  CreditCardDto getCreditCardBalance(String cardNumber);

  void addIncome(String cardNumber, double income);

  void closeCreditCard(String cardNumber);
}
