package org.wickedsource.coderadar.annotation.api;

public class CommitAssociatedMetricValue extends MetricValue {

    private Commit commit;

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

}
