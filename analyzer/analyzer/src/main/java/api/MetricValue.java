package api;

import org.wickedsource.coderadar.analyzer.api.Metric;

public class MetricValue {

    private Metric metric;

    private long value;

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long change) {
        this.value = change;
    }
}
