package org.wickedsource.coderadar.metric.rest.metricvalue.commits;

import org.wickedsource.coderadar.metric.rest.metricvalue.QueryOutput;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Objects of this class provide parameters to query for metric values aggregated per commit.
 */
public class CommitMetricsQuery {

    @NotNull
    @Size(min = 1)
    private List<String> commits;

    @NotNull
    private QueryOutput outputs = new QueryOutput();

    public CommitMetricsQuery() {
    }

    public QueryOutput getOutputs() {
        return outputs;
    }

    public void setOutputs(QueryOutput outputs) {
        this.outputs = outputs;
    }

    public boolean outputMetrics() {
        return outputs.getMetrics() != null && !outputs.getMetrics().isEmpty();
    }

    public boolean outputQualityProfiles() {
        return outputs.getProfiles() != null && !outputs.getProfiles().isEmpty();
    }

    public List<String> getCommits() {
        return commits;
    }

    public void setCommits(List<String> commits) {
        this.commits = commits;
    }

    public void addCommit(String commit) {
        initCommits();
        commits.add(commit);
    }

    private void initCommits() {
        if (commits == null) {
            commits = new ArrayList<>();
        }
    }

    public void addCommits(String... commits) {
        initCommits();
        this.commits.addAll(Arrays.asList(commits));
    }

}

