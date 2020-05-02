package banking.dao;

public interface RegistrationTokenDao<T> {

  void insertTokenData(T entity);

  T getTokenByAccountId(String token);
}
