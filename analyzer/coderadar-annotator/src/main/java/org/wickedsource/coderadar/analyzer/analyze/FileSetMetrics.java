package org.wickedsource.coderadar.analyzer.analyze;

import org.wickedsource.coderadar.analyzer.plugin.api.FileMetrics;
import org.wickedsource.coderadar.analyzer.walk.FileMetricsWithChangeType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Stores metrics to each of a set of source code files.
 */
public class FileSetMetrics {

    private Map<String, FileMetricsWithChangeType> fileMap = new HashMap<>();

    public FileMetricsWithChangeType getFileMetrics(String file) {
        return fileMap.get(file);
    }

    public void addMetricsToFile(String file, FileMetricsWithChangeType metrics) {
        FileMetrics fileMetrics = fileMap.get(file);
        if (fileMetrics == null) {
            fileMap.put(file, metrics);
        } else {
            fileMetrics = fileMap.get(file);
            fileMetrics.add(metrics);
        }
    }

    public Set<String> getFiles() {
        return fileMap.keySet();
    }

}
