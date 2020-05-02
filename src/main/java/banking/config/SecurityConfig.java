package banking.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Qualifier(value = "userDetailsServiceImpl")
  private final UserDetailsService userDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.headers().cacheControl();
    http
        .csrf().disable()
        .authorizeRequests()
          //all users to have access to these pages
          .antMatchers("/account/create", "/account/registrationConfirm", "/login").permitAll()
        //all other pages requires authentication
        .anyRequest().authenticated()

        .and()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/perform_login")
        .defaultSuccessUrl("/homepage.html", true)
        .failureUrl("/login.html?error=true")
//        .failureHandler(authenticationFailureHandler())

        .and()
        .logout()
        .logoutUrl("/perform_logout")
        .deleteCookies("JSESSIONID")
//        .logoutSuccessHandler(logoutSuccessHandler());

        .and()
        .rememberMe().key("uniqueAndSecret");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}

