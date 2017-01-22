package org.wickedsource.coderadar.factories.entities;

import java.util.Date;
import org.wickedsource.coderadar.analyzingstrategy.domain.AnalyzingStrategy;

public class AnalyzingStrategyFactory {

  public AnalyzingStrategy analyzingStrategy() {
    AnalyzingStrategy strategy = new AnalyzingStrategy();
    strategy.setFromDate(new Date());
    strategy.setActive(true);
    return strategy;
  }
}
