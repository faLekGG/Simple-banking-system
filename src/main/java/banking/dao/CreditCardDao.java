package banking.dao;

public interface CreditCardDao<T> {

  void insertCreditCardData(T entity, int userId);

  T findCreditCard(String cardNumber);

  double getCreditCardBalance(String cardNumber);

  void updateBalance(String cardNumber, double amount);

  void deleteCard(String cardNumber);
}
