package org.wickedsource.coderadar.analyzer.api;

import java.io.InputStream;

public interface AdapterPlugin extends AnalyzerPlugin {

    CommitMetrics mapResultsFile(String modulePath, InputStream resultFile);

}
