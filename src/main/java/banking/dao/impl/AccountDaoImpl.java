package banking.dao.impl;

import banking.constant.Queries;
import banking.dao.AccountDao;
import banking.dao.converter.DateTimeConverter;
import banking.dao.mapper.AccountRowMapper;
import banking.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountDaoImpl implements AccountDao<Account> {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final AccountRowMapper accountRowMapper;
  private final DateTimeConverter dateTimeConverter;

  @Override
  public void insertData(Account account) {
    SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(account);
    jdbcTemplate.update(Queries.POPULATE_ACCOUNT, sqlParameterSource);
  }

  @Override
  public Account findAccountByUserName(String userName) {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("username", userName);
    SqlRowSet rowSet = jdbcTemplate.queryForRowSet(Queries.QUERY_ACCOUNT_BY_USERNAME, namedParameters);
    return mapRowSetToAccount(rowSet);
  }

  @Override
  public Account findAccountById(long id) {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("id", id);
    SqlRowSet rowSet = jdbcTemplate.queryForRowSet(Queries.QUERY_ACCOUNT_BY_ID, namedParameters);
    return mapRowSetToAccount(rowSet);
  }

  @Override
  public void enableAccountById(long id) {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("id", id);
    jdbcTemplate.update(Queries.QUERY_UPDATE_ACCOUNT_BY_ID, namedParameters);
  }

  @Override
  public int findAccountIdByUserName(String userName) {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("username", userName);
    return jdbcTemplate.query(Queries.QUERY_ACCOUNT_ID, namedParameters, rs -> {
      if (rs.next()) {
        return rs.getInt("id");
      }
      return -1;
    });
  }

  private Account mapRowSetToAccount(SqlRowSet rowSet) {
    if (rowSet.next()) {
      Account account = new Account();
      account.setId(rowSet.getLong("id"));
      account.setUsername(rowSet.getString("username"));
      account.setPassword(rowSet.getString("password"));
      account.setEmail(rowSet.getString("email"));
      account.setCreatedOn(dateTimeConverter.convertToEntityAttribute(rowSet.getTimestamp("created_on")));
      account.setLastLogin(dateTimeConverter.convertToEntityAttribute(rowSet.getTimestamp("last_login")));
      return account;
    }
    return null;
  }
}
