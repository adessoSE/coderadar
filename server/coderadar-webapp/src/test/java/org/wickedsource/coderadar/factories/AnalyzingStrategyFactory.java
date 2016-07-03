package org.wickedsource.coderadar.factories;

import org.wickedsource.coderadar.analyzingstrategy.domain.AnalyzingStrategy;
import org.wickedsource.coderadar.analyzingstrategy.rest.AnalyzingStrategyResource;

import java.util.Date;

public class AnalyzingStrategyFactory {

    public AnalyzingStrategyResource analyzingStrategyResource(){
        return new AnalyzingStrategyResource(new Date(), true);
    }

    public AnalyzingStrategy analyzingStrategy() {
        AnalyzingStrategy strategy = new AnalyzingStrategy();
        strategy.setFromDate(new Date());
        strategy.setActive(true);
        return strategy;
    }
}
