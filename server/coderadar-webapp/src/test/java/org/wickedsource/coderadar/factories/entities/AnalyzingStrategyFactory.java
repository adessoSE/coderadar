package org.wickedsource.coderadar.factories.entities;

import org.wickedsource.coderadar.analyzingstrategy.domain.AnalyzingStrategy;

import java.util.Date;

public class AnalyzingStrategyFactory {

    public AnalyzingStrategy analyzingStrategy() {
        AnalyzingStrategy strategy = new AnalyzingStrategy();
        strategy.setFromDate(new Date());
        strategy.setActive(true);
        return strategy;
    }
}
