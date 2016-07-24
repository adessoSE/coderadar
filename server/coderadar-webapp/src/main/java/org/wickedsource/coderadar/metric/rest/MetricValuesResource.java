package org.wickedsource.coderadar.metric.rest;

import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.metric.domain.MetricValueDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetricValuesResource extends ResourceSupport {

    private Map<String, List<MetricValueDTO>> metrics = new HashMap<>();

    public void addMetricsForCommit(String commitName, List<MetricValueDTO> values) {
        List<MetricValueDTO> metricValues = metrics.get(commitName);
        if (metricValues == null) {
            metricValues = new ArrayList<>();
            metrics.put(commitName, metricValues);
        }
        metricValues.addAll(values);
    }

    public List<MetricValueDTO> getMetricsForCommit(String commitName) {
        return metrics.get(commitName);
    }


}
