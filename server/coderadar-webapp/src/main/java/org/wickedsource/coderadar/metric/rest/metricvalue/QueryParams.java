package org.wickedsource.coderadar.metric.rest.metricvalue;

import javax.validation.constraints.NotNull;

/**
 * Objects of this class provide parameters to query for metric values.
 */
public class QueryParams {

    @NotNull
    private QuerySubjectParams subjects = new QuerySubjectParams();

    @NotNull
    private QueryOutputParams outputs = new QueryOutputParams();

    public QueryParams() {
    }

    public QuerySubjectParams getSubjects() {
        return subjects;
    }

    public void setSubjects(QuerySubjectParams subjects) {
        this.subjects = subjects;
    }

    public QueryOutputParams getOutputs() {
        return outputs;
    }

    public void setOutputs(QueryOutputParams outputs) {
        this.outputs = outputs;
    }

    public boolean scanCommits() {
        return subjects.getCommits() != null && !subjects.getCommits().isEmpty();
    }

    public boolean outputMetrics() {
        return outputs.getMetrics() != null && !outputs.getMetrics().isEmpty();
    }

    public boolean outputQualityProfiles() {
        return outputs.getProfiles() != null && !outputs.getProfiles().isEmpty();
    }

}

