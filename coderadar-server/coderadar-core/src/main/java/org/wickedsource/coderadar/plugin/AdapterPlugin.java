package org.wickedsource.coderadar.plugin;

import java.io.InputStream;

public interface AdapterPlugin extends AnalyzerPlugin {

  FileSetMetrics mapReportFile(InputStream resultFile) throws AnalyzerException;
}
