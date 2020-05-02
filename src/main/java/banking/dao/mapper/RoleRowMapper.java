package banking.dao.mapper;

import banking.entity.Role;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoleRowMapper implements RowMapper<Role> {

  @Override
  public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
    Role role = new Role();
    role.setId((long) rs.getInt("id"));
    role.setName(rs.getString("name"));
    return role;
  }
}
