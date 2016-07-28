package org.wickedsource.coderadar.metric.rest.metricvalue;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuerySubjectParams {

    @Size(min = 1)
    private List<String> commits;

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
