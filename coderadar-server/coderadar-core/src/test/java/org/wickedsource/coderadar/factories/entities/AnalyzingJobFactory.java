package org.wickedsource.coderadar.factories.entities;

import java.util.Date;
import org.wickedsource.coderadar.analyzingjob.domain.AnalyzingJob;

public class AnalyzingJobFactory {

  public AnalyzingJob analyzingJob() {
    AnalyzingJob strategy = new AnalyzingJob();
    strategy.setFromDate(new Date());
    strategy.setActive(true);
    return strategy;
  }
}
