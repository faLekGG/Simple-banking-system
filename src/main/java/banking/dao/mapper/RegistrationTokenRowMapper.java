package banking.dao.mapper;

import banking.dao.converter.DateTimeConverter;
import banking.entity.RegistrationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class RegistrationTokenRowMapper implements RowMapper<RegistrationToken> {

  private final DateTimeConverter dateTimeConverter;

  @Override
  public RegistrationToken mapRow(ResultSet rs, int rowNum) throws SQLException {
    RegistrationToken registrationToken = new RegistrationToken();
    registrationToken.setId(rs.getLong("id"));
    registrationToken.setTokenName(rs.getString("number"));
    registrationToken.setExpirationDate(dateTimeConverter.convertToEntityAttribute(rs.getTimestamp("expiration_date")));
    registrationToken.setAccountId(rs.getLong("account_id"));
    return registrationToken;
  }
}
