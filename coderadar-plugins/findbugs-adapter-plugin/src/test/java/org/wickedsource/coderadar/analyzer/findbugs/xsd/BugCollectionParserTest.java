package org.wickedsource.coderadar.analyzer.findbugs.xsd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BugCollectionParserTest extends TestReportAccessor {

  @Test
  public void parsesSuccessfully() throws Exception {
    byte[] report = getValidReport();

    BugCollectionParser parser = new BugCollectionParser();
    BugCollection collection = parser.fromBytes(report);

    Assertions.assertNotNull(collection);
  }
}
