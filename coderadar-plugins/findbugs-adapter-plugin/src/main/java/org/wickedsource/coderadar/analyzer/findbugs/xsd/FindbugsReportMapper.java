package org.wickedsource.coderadar.analyzer.findbugs.xsd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.wickedsource.coderadar.analyzer.api.FileMetrics;
import org.wickedsource.coderadar.analyzer.api.FileSetMetrics;
import org.wickedsource.coderadar.analyzer.api.Finding;
import org.wickedsource.coderadar.analyzer.api.Metric;

public class FindbugsReportMapper {

  public static final String FINDBUGS_METRIC_PREFIX = "findbugs:";

  public FileSetMetrics mapReport(byte[] report) throws JAXBException {
    BugCollectionParser parser = new BugCollectionParser();
    BugCollection bugCollection = parser.fromBytes(report);

    FileSetMetrics filesetMetrics = new FileSetMetrics();

    for (BugCollection.BugInstance bug : bugCollection.getBugInstance()) {
      List<Finding> findings = new ArrayList<>();

      String filePath = null;

      for (Object object : bug.getClazzOrTypeOrMethod()) {
        if (object instanceof SourceLine) {
          SourceLine sourceLine = (SourceLine) object;
          Finding finding = new Finding(sourceLine.getStart(), sourceLine.getEnd());
          findings.add(finding);
        } else if (object instanceof BugCollection.BugInstance.Class) {
          filePath = ((BugCollection.BugInstance.Class) object).getSourceLine().getSourcepath();
        }
      }

      if (filePath == null) {
        throw new IllegalStateException(
            "Expecting for each BugInstance to have a <Class> subelement!");
      }

      Metric metric = metric(bug.getType());
      FileMetrics fileMetrics = new FileMetrics();
      fileMetrics.addFindings(metric, findings);

      filesetMetrics.addMetricsToFile(filePath, fileMetrics);
    }

    return filesetMetrics;
  }

  private Metric metric(String bugType) {
    return new Metric(FINDBUGS_METRIC_PREFIX + bugType);
  }
}
