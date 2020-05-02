package banking.constant;

public final class Queries {

  public static final String POPULATE_CARD_TABLE = "INSERT INTO credit_card(number, pin, balance, account_id) "
      + "VALUES(:cardNumber, :pinCode, :balance, :userId)";

  public static final String QUERY_CREDIT_CARD = "SELECT id, number, pin, balance "
      + "FROM credit_card WHERE number = :cardNumber";

  public static final String UPDATE_CARD_BALANCE = "UPDATE credit_card SET balance = :balance "
      + "WHERE number = :cardNumber";

  public static final String GET_CARD_BALANCE = "SELECT balance FROM credit_card WHERE number = :cardNumber";

  public static final String DELETE_CREDIT_CARD = "DELETE FROM credit_card WHERE number = :cardNumber";

  public static final String POPULATE_ACCOUNT =
      "INSERT INTO account(username, password, email, created_on, last_login) "
          + "VALUES(:username, :password, :email, :createdOn, :lastLogin)";

  public static final String QUERY_ACCOUNT_BY_USERNAME =
      "SELECT id, username, password, email, created_on, last_login, enabled "
          + "FROM account WHERE username = :username";

  public static final String QUERY_ACCOUNT_BY_ID =
      "SELECT id, username, password, email, created_on, last_login, enabled "
          + "FROM account WHERE id = :id";

  public static final String QUERY_UPDATE_ACCOUNT_BY_ID = "UPDATE account SET enabled = true WHERE id = :id";

  public static final String QUERY_ACCOUNT_ID = "SELECT id FROM account WHERE username = :username";

  public static final String QUERY_ALL_ROLES = "SELECT * FROM role";

  public static final String QUERY_DEFAULT_ROLE = "SELECT id, name FROM role WHERE def = true";

  public static final String INSERT_INTO_ACCOUNT_ROLE = "INSERT INTO account_role(account_id, role_id) "
      + "VALUES(:account_id, :role_id)";

  public static final String QUERY_FOR_ACCOUNT_ROLES = "SELECT name FROM role "
      + "INNER JOIN account_role ON account_role.role_id = role.id "
      + "INNER JOIN account ON account_role.account_id = account.id"
      + "WHERE username = :username";

  public static final String INSERT_INTO_REGISTRATION_TOKEN = "INSERT INTO registration_token"
      + "(id, token_name, expiration_date, account_id) "
      + "VALUES(:id, :tokenName, :expirationDate, :accountId)";

  public static final String QUERY_REGISTRATION_TOKEN = "SELECT * FROM registration_token WHERE token_name = :token";

  private Queries() {

  }
}
