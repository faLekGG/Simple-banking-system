package banking.dao.impl;

import banking.constant.Queries;
import banking.dao.RoleDao;
import banking.dao.mapper.RoleRowMapper;
import banking.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoleDaoImpl implements RoleDao<Role> {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final RoleRowMapper roleRowMapper;

  @Override
  public List<Role> findAllRoles() {
    return jdbcTemplate.queryForList(Queries.QUERY_ALL_ROLES, new MapSqlParameterSource(), Role.class);
  }

  @Override
  public List<Role> findAccountRoles(String userName) {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("username", userName);
    return jdbcTemplate.queryForList(Queries.QUERY_FOR_ACCOUNT_ROLES, namedParameters, Role.class);
  }

  @Override
  public Role findDefaultRole() {
    return jdbcTemplate.queryForObject(Queries.QUERY_DEFAULT_ROLE, new MapSqlParameterSource(), roleRowMapper);
  }

  @Override
  public void insertAccountRoles(long userId, long roleId) {
    SqlParameterSource namedParameters = new MapSqlParameterSource()
        .addValue("account_id", userId)
        .addValue("role_id", roleId);
    jdbcTemplate.update(Queries.INSERT_INTO_ACCOUNT_ROLE, namedParameters);
  }
}
