package io.reflectoring.coderadar.analyzer.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.checks.coding.ReturnCountCheck;
import com.puppycrawl.tools.checkstyle.checks.metrics.CyclomaticComplexityCheck;
import com.puppycrawl.tools.checkstyle.checks.metrics.JavaNCSSCheck;
import com.puppycrawl.tools.checkstyle.checks.metrics.NPathComplexityCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.ExecutableStatementCountCheck;
import java.lang.reflect.Field;

/**
 * This class is an ugly helper class to extract numeric values from checkstyle's AuditEvents. The
 * values are hidden in String messages and are not accessible via the API so this class does some
 * ugly reflection.
 */
public class MetricCountExtractor {

  /**
   * Extracts the metric count from a Checkstyle AuditEvent.
   *
   * <p>An AuditEvent contains a message which describes some coding violation. Usually, each of
   * these messages represents a single violation. Some messages however represent a number of
   * violations. For example the message for CyclomaticComplexityCheck contains a complexity for a
   * method (if it is higher than a configured threshold, otherwise it is not reported).
   *
   * @param event the Checkstyle event to extract a metric count from.
   * @return the count of the metric that is reported by the Checkstyle event.
   */
  public Long extractMetricCount(AuditEvent event) {
    try {

      if (event.getSourceName().equals(CyclomaticComplexityCheck.class.getName())
          || event.getSourceName().equals(ReturnCountCheck.class.getName())
          || event.getSourceName().equals(JavaNCSSCheck.class.getName())
          || event.getSourceName().equals(NPathComplexityCheck.class.getName())
          || event.getSourceName().equals(ExecutableStatementCountCheck.class.getName())) {
        return extractLongArgument(event, 0);
      }

      // by default return 1 which means we are just counting the events
      return 1L;
    } catch (NoSuchFieldException | IllegalAccessException e) {
      return -1L;
    }
  }

  private Long extractLongArgument(AuditEvent event, int position)
      throws NoSuchFieldException, IllegalAccessException {
    Field argsField = LocalizedMessage.class.getDeclaredField("args");
    argsField.setAccessible(true);
    Object[] args = (Object[]) argsField.get(event.getLocalizedMessage());
    Number count = (Number) args[position];
    return count.longValue();
  }
}
