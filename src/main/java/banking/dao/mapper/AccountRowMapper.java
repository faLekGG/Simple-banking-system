package banking.dao.mapper;

import banking.dao.converter.DateTimeConverter;
import banking.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class AccountRowMapper implements RowMapper<Account> {

  private final DateTimeConverter dateTimeConverter;

  @Override
  public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
    if (rs.next()) {
      Account account = new Account();
      account.setId(rs.getLong("id"));
      account.setUsername(rs.getString("username"));
      account.setPassword(rs.getString("password"));
      account.setEmail(rs.getString("email"));
      account.setCreatedOn(dateTimeConverter.convertToEntityAttribute(rs.getTimestamp("created_on")));
      account.setLastLogin(dateTimeConverter.convertToEntityAttribute(rs.getTimestamp("last_login")));
      return account;
    }
    return null;
  }
}
