package org.wickedsource.coderadar.annotation.api;

import org.wickedsource.coderadar.analyzer.api.Metric;

import java.util.HashMap;
import java.util.Map;

public class RepositoryMetrics {

    private Map<String, Map<Metric, MetricValue>> metrics = new HashMap<>();
}
