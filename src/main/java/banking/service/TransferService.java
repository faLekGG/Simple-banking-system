package banking.service;

import banking.dto.TransferDto;
import banking.exception.TransferException;

public interface TransferService {

  void doTransfer(TransferDto transferDto) throws TransferException;
}
