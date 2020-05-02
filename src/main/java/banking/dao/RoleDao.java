package banking.dao;

import java.util.List;

public interface RoleDao<T> {

  List<T> findAllRoles();

  T findDefaultRole();

  List<T> findAccountRoles(String userName);

  void insertAccountRoles(long userId, long roleId);
}
