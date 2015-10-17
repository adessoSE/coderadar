package org.wickedsource.coderadar.analyzer.plugin.api;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

public interface AnalyzerPlugin {

    void configure(Properties properties);

    MetricResults analyzeFile(InputStream in);

    MetricResults analyzeFiles(File folder, ProgressListener progressListener);

}
