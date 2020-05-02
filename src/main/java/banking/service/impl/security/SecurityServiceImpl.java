package banking.service.impl.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityServiceImpl implements SecurityService {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;

  @Override
  public String discoverLoggedInUserName() {
    Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
    if (userDetails instanceof UserDetails) {
      return ((UserDetails) userDetails).getUsername();
    }

    return null;
  }

  @Override
  public void doAutoLogin(String username, String password) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        userDetails, password, userDetails.getAuthorities());

    authenticationManager.authenticate(usernamePasswordAuthenticationToken);

    if (usernamePasswordAuthenticationToken.isAuthenticated()) {
      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      log.debug(String.format("Auto login %s successfully!", username));
    }
  }
}
