package org.wickedsource.coderadar.analyzer.annotate;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.AnyObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.coderadar.analyzer.analyze.CommitMetrics;
import org.wickedsource.coderadar.analyzer.json.GsonFactory;
import org.wickedsource.coderadar.analyzer.walk.FileMetricsWithChangeType;

/**
 * Stores a file's metrics as a git note on the file in the git repository.
 */
public class AnnotatingMetricsProcessor implements MetricsProcessor {

    private Logger logger = LoggerFactory.getLogger(AnnotatingMetricsProcessor.class);

    private Gson gson;

    public AnnotatingMetricsProcessor() {
        gson = GsonFactory.getInstance().createGson();
    }

    @Override
    public void processMetrics(FileMetricsWithChangeType fileMetrics, Git gitClient, AnyObjectId commitId, String filePath) {
        String json = NoteUtil.getInstance().getCoderadarNote(gitClient, commitId.getName());

        CommitMetrics commitMetrics;
        if (json == null || "".equals(json)) {
            logger.info("No note found for commit {}. Creating a new one.", commitId.getName());
            commitMetrics = new CommitMetrics();
        } else {
            // reading existing metrics from commit note
            try {
                commitMetrics = gson.fromJson(json, CommitMetrics.class);
            } catch (JsonSyntaxException e) {
                logger.warn(String.format("Encountered invalid JSON in note of commit %s! Overwriting the note with new metrics.", commitId.getName()));
                commitMetrics = new CommitMetrics();
            }
        }

        // adding to existing metrics
        commitMetrics.addMetricsToFile(filePath, fileMetrics);

        // writing new metrics back to commit note
        String newJson = gson.toJson(commitMetrics);
        NoteUtil.getInstance().setCoderadarNote(gitClient, commitId.getName(), newJson);
    }
}
