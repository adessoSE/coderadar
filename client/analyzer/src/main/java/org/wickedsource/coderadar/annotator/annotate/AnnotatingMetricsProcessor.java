package org.wickedsource.coderadar.annotator.annotate;

import com.google.gson.Gson;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.AnyObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.coderadar.analyzer.api.FileMetricsWithChangeType;
import org.wickedsource.coderadar.analyzer.api.FileSetMetrics;
import org.wickedsource.coderadar.annotator.serialize.GsonFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores a file's metrics as a git note on the file in the git repository.
 */
public class AnnotatingMetricsProcessor implements MetricsProcessor {

    private Logger logger = LoggerFactory.getLogger(AnnotatingMetricsProcessor.class);

    private Gson gson;

    private Map<String, FileSetMetrics> metricsForCommit = new HashMap<>();

    public AnnotatingMetricsProcessor() {
        gson = GsonFactory.getInstance().createGson();
    }

    @Override
    public void processMetrics(FileMetricsWithChangeType fileMetrics, Git gitClient, AnyObjectId commitId, String filePath) {
        FileSetMetrics fileSetMetrics = metricsForCommit.get(commitId.getName());
        if (fileSetMetrics == null) {
            fileSetMetrics = new FileSetMetrics();
            metricsForCommit.put(commitId.getName(), fileSetMetrics);
        }

        // adding to existing metrics
        fileSetMetrics.addMetricsToFile(filePath, fileMetrics);
    }

    @Override
    public void onCommitFinished(Git gitClient, AnyObjectId commitId) {
        FileSetMetrics fileSetMetrics = metricsForCommit.get(commitId.getName());
        if (fileSetMetrics == null) {
            fileSetMetrics = new FileSetMetrics();
        }
        String json = gson.toJson(fileSetMetrics);
        NoteUtil.getInstance().setCoderadarNote(gitClient, commitId.getName(), json);
        metricsForCommit.remove(commitId.getName());
    }
}
