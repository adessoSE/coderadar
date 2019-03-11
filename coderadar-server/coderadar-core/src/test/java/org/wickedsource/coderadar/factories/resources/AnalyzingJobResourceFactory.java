package org.wickedsource.coderadar.factories.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.wickedsource.coderadar.analyzingjob.rest.AnalyzingJobResource;

public class AnalyzingJobResourceFactory {

  public AnalyzingJobResource analyzingJobResource() {
    try {
      Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-01");
      AnalyzingJobResource strategy = new AnalyzingJobResource(date, true);
      strategy.setRescan(Boolean.TRUE);
      return strategy;
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
