package banking.dao.impl;

import banking.constant.Queries;
import banking.dao.RegistrationTokenDao;
import banking.dao.mapper.RegistrationTokenRowMapper;
import banking.entity.RegistrationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class RegistrationTokenDaoImpl implements RegistrationTokenDao<RegistrationToken> {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final RegistrationTokenRowMapper registrationTokenRowMapper;

  @Override
  public void insertTokenData(RegistrationToken entity) {
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
    jdbcTemplate.update(Queries.INSERT_INTO_REGISTRATION_TOKEN, parameterSource);
  }

  @Override
  public RegistrationToken getTokenByAccountId(String token) {
    SqlParameterSource sqlParameterSource = new MapSqlParameterSource("token", token);
    return jdbcTemplate
        .queryForObject(Queries.QUERY_REGISTRATION_TOKEN, sqlParameterSource, registrationTokenRowMapper);
  }
}
