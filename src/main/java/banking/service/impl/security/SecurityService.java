package banking.service.impl.security;

/**
 * SecurityService to provide current logged-in user and auto login user after registration.
 */
public interface SecurityService {

  String discoverLoggedInUserName();

  void doAutoLogin(String username, String password);
}
