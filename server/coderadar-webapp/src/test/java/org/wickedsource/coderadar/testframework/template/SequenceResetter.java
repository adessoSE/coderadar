package org.wickedsource.coderadar.testframework.template;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class SequenceResetter {

  @Autowired private DataSource dataSource;

  /**
   * Resets the sequences of the given database tables. To be used to reset the database state after
   * tests that create new entities.
   */
  public void resetAutoIncrementColumns(String... tableNames) {
    try {
      String resetSqlTemplate = "ALTER TABLE %s ALTER COLUMN id RESTART WITH 1";
      try (Connection dbConnection = dataSource.getConnection()) {
        for (String resetSqlArgument : tableNames) {
          try (Statement statement = dbConnection.createStatement()) {
            String resetSql = String.format(resetSqlTemplate, resetSqlArgument);
            statement.execute(resetSql);
          }
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
