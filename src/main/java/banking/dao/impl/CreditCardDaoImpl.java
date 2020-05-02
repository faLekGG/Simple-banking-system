package banking.dao.impl;

import banking.constant.Queries;
import banking.dao.CreditCardDao;
import banking.dao.mapper.CreditCardRowMapper;
import banking.entity.CreditCard;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CreditCardDaoImpl implements CreditCardDao<CreditCard> {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final CreditCardRowMapper creditCardRowMapper;

  @Override
  public void insertCreditCardData(CreditCard entity, int userId) {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("cardNumber", entity.getCardNumber())
        .addValue("pinCode", entity.getPinCode())
        .addValue("balance", entity.getBalance())
        .addValue("userId", userId);
    jdbcTemplate.update(Queries.POPULATE_CARD_TABLE, namedParameters);
  }

  public CreditCard findCreditCard(String cardNumber) {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("cardNumber", cardNumber);
    return jdbcTemplate.queryForObject(Queries.QUERY_CREDIT_CARD, namedParameters, creditCardRowMapper);
  }

  @Override
  public double getCreditCardBalance(String cardNumber) {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("cardNumber", cardNumber);
    return jdbcTemplate.query(Queries.GET_CARD_BALANCE, namedParameters, rs -> {
      if (rs.next()) {
        return rs.getDouble("balance");
      }
      return 0.0;
    });
  }

  public void updateBalance(String cardNumber, double amount) {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("cardNumber", cardNumber)
        .addValue("balance", amount);
    jdbcTemplate.update(Queries.UPDATE_CARD_BALANCE, namedParameters);
  }

  @Override
  public void deleteCard(String cardNumber) {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("cardNumber", cardNumber);
    jdbcTemplate.update(Queries.DELETE_CREDIT_CARD, namedParameters);
  }
}
