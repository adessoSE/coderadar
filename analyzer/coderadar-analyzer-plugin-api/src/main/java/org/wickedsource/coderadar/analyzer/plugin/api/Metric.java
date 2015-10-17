package org.wickedsource.coderadar.analyzer.plugin.api;

public class Metric<T> {

    private final String id;

    private MetricType type;

    public Metric(String id, MetricType type){
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Metric metric = (Metric) o;

        return !(id != null ? !id.equals(metric.id) : metric.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
