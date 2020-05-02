package banking.dao.converter;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class DateTimeConverter {

  public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
    return localDateTime != null ? Timestamp.valueOf(localDateTime) : null;
  }

  public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
    return timestamp != null ? timestamp.toLocalDateTime() : null;
  }
}