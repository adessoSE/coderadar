package org.wickedsource.coderadar.factories.resources;

import org.wickedsource.coderadar.analyzingstrategy.rest.AnalyzingStrategyResource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AnalyzingStrategyResourceFactory {

    public AnalyzingStrategyResource analyzingStrategyResource(){
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-01");
            AnalyzingStrategyResource strategy = new AnalyzingStrategyResource(date, true);
            strategy.setRescan(Boolean.TRUE);
            return strategy;
        }catch(ParseException e){
            throw new RuntimeException(e);
        }
    }

}
