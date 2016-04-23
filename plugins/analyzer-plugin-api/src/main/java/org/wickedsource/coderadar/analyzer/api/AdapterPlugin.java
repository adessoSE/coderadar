package org.wickedsource.coderadar.analyzer.api;

import java.io.InputStream;

public interface AdapterPlugin extends AnalyzerPlugin {

    FileSetMetrics mapResultsFile(InputStream resultFile);

}
