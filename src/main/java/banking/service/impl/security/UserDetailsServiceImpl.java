package banking.service.impl.security;

import banking.dao.AccountDao;
import banking.dao.RoleDao;
import banking.entity.Account;
import banking.entity.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * To implement login/authentication with Spring Security, we need to implement org.springframework.security.core.userdetails.UserDetailsService
 * interface
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

  private final AccountDao<Account> accountDao;
  private final RoleDao<Role> roleDao;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    Account account = accountDao.findAccountByUserName(userName);
    if (account == null) {
      log.error(String.format("Account with userName %s was not found", userName));
      throw new UsernameNotFoundException("Account was not found with " + userName);
    }

    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    roleDao.findAccountRoles(account.getUsername())
        .forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));

    return new User(account.getUsername(), account.getPassword(), account.isEnabled(), true,
        true, true, grantedAuthorities);
  }
}
