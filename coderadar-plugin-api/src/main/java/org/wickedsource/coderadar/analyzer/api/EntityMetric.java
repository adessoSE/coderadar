package org.wickedsource.coderadar.analyzer.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EntityMetric<T> {

    private String metricName;
    private T metricObject;
}
