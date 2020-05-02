package banking.dao;

public interface AccountDao<T> {

  void insertData(T account);

  T findAccountByUserName(String userName);

  T findAccountById(long id);

  void enableAccountById(long id);

  int findAccountIdByUserName(String userName);
}
