package org.wickedsource.coderadar.analyzer.findbugs.xsd;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

public class TestReportAccessor {

  protected byte[] getValidReport() throws IOException {
    return getReport("valid-findbugs-report.xml");
  }

  protected byte[] getInvalidReport() throws IOException {
    return getReport("invalid-findbugs-report.xml");
  }

  private byte[] getReport(String pathInClasspath) throws IOException {
    InputStream validReport = getClass().getResourceAsStream(pathInClasspath);
    if (validReport == null) {
      throw new IllegalStateException("Error in test! Input file is missnig!");
    }
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    IOUtils.copy(validReport, byteOut);
    byte[] report = byteOut.toByteArray();
    return report;
  }
}
