package org.wickedsource.coderadar.analyzer.plugin.api;

public class Metric {

    private final String id;

    public Metric(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String toString() {
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
