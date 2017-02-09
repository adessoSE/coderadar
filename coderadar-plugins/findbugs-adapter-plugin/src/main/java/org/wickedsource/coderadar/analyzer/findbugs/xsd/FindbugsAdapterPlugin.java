package org.wickedsource.coderadar.analyzer.findbugs.xsd;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.wickedsource.coderadar.analyzer.api.AdapterPlugin;
import org.wickedsource.coderadar.analyzer.api.AnalyzerException;
import org.wickedsource.coderadar.analyzer.api.FileSetMetrics;
import org.xml.sax.SAXParseException;

public class FindbugsAdapterPlugin implements AdapterPlugin {

  @Override
  public FileSetMetrics mapReportFile(InputStream reportFile) throws AnalyzerException {
    try {
      ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
      IOUtils.copy(reportFile, byteOut);
      byte[] reportBytes = byteOut.toByteArray();
      validateReport(reportBytes);
      FindbugsReportMapper mapper = new FindbugsReportMapper();
      return mapper.mapReport(reportBytes);
    } catch (IOException | JAXBException e) {
      throw new AnalyzerException(e);
    }
  }

  private void validateReport(byte[] report) throws AnalyzerException {
    FindbugsReportXsdValidator validator = new FindbugsReportXsdValidator();
    List<SAXParseException> validationErrors =
        validator.validate(report, FindbugsReportXsdValidator.ValidationLevel.FATAL);
    if (!validationErrors.isEmpty()) {
      throw new AnalyzerException(
          "Not a valid Findbugs report! First XSD validation error:", validationErrors.get(0));
    }
  }
}
