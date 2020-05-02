package banking.dao.mapper;

import banking.entity.CreditCard;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CreditCardRowMapper implements RowMapper<CreditCard> {

  @Override
  public CreditCard mapRow(ResultSet rs, int rowNum) throws SQLException {
    CreditCard creditCard = new CreditCard();
    creditCard.setId(rs.getLong("id"));
    creditCard.setCardNumber(rs.getString("number"));
    creditCard.setBalance(rs.getDouble("balance"));
    creditCard.setPinCode(rs.getString("pin"));
    return creditCard;
  }
}
