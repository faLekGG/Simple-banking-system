package banking.service.impl;

import banking.dao.CreditCardDao;
import banking.dto.TransferDto;
import banking.entity.CreditCard;
import banking.exception.TransferException;
import banking.service.ChecksumGeneratorService;
import banking.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

  private final CreditCardDao<CreditCard> creditCardDao;
  private final ChecksumGeneratorService checksumGeneratorService;

  @Transactional
  @Override
  public void doTransfer(TransferDto transferDto) throws TransferException {
    CreditCard fromCreditCard = creditCardDao.findCreditCard(transferDto.getCardFrom());
    CreditCard toCreditCard = creditCardDao.findCreditCard(transferDto.getCardTo());

    if (toCreditCard == null || fromCreditCard == null) {
      log.error("One of the card do not exist.");
      throw new TransferException("One of the card do not exist.");
    }

    if (Objects.equals(fromCreditCard.getCardNumber(), toCreditCard.getCardNumber())) {
      log.error("You cannot transfer money to the same account!");
      throw new TransferException("You cannot transfer money to the same account!");
    }

    if (!checksumGeneratorService.verifyCreditCard(fromCreditCard.getCardNumber())
        || !checksumGeneratorService.verifyCreditCard(toCreditCard.getCardNumber())) {
      log.error("Probably you made mistake in card number. Please try again!");
      throw new TransferException("Probably you made mistake in card number. Please try again!");
    }

    double fromCardBalance = fromCreditCard.getBalance() - transferDto.getAmount();
    double toCardBalance = toCreditCard.getBalance() + transferDto.getAmount();

    creditCardDao.updateBalance(fromCreditCard.getCardNumber(), fromCardBalance);
    creditCardDao.updateBalance(toCreditCard.getCardNumber(), toCardBalance);
  }
}
