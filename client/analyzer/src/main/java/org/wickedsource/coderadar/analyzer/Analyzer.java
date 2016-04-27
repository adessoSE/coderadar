package org.wickedsource.coderadar.analyzer;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wickedsource.coderadar.analyzer.api.AnalyzerException;
import org.wickedsource.coderadar.analyzer.api.FileAnalyzerPlugin;
import org.wickedsource.coderadar.analyzer.api.FileMetrics;
import org.wickedsource.coderadar.analyzer.api.FileSetMetrics;
import org.wickedsource.coderadar.analyzer.match.FileMatchingPattern;
import org.wickedsource.coderadar.analyzer.match.FileWalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Analyzer {

    private Logger logger = LoggerFactory.getLogger(Analyzer.class);

    public FileSetMetrics analyze(AnalyzerConfiguration config) throws AnalyzerException {

        AnalyzerPluginRegistry pluginRegistry = new AnalyzerPluginRegistry();
        pluginRegistry.initializeAnalyzers(config.getPluginProperties());

        FileSetMetrics metrics = analyzeSourceCodeFiles(config.getSourceCodeFiles(), pluginRegistry.getRegisteredFileAnalyzers());
        return metrics;
    }

    private FileSetMetrics analyzeSourceCodeFiles(FileMatchingPattern sourceCodeFiles, final List<FileAnalyzerPlugin> fileAnalyzerPlugins) {
        final FileSetMetrics fileSetMetrics = new FileSetMetrics();
        try {
            if (sourceCodeFiles == null) {
                logger.info("no source code files to analyze - skipping FileAnalyzerPlugins ...");
            } else {
                final FileAnalyzer fileAnalyzer = new FileAnalyzer();
                FileWalker sourceCodeWalker = new FileWalker() {
                    @Override
                    public void processFile(Path file) {
                        try {
                            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                            Files.copy(file, byteOut);
                            byte[] fileContent = byteOut.toByteArray();
                            FileMetrics fileMetrics = fileAnalyzer.analyzeFile(fileAnalyzerPlugins, file.toAbsolutePath().toString(), fileContent);
                            fileSetMetrics.addMetricsToFile(file.toString().replaceAll("\\\\", "/"), fileMetrics);
                        } catch (IOException e) {
                            logger.warn("skipping analysis of file {} due to IOException: {}", file, e);
                        }
                    }
                };
                sourceCodeWalker.walk(sourceCodeFiles);
            }
        } catch (IOException e) {
            logger.error("exception while walking source code files", e);
        }
        return fileSetMetrics;
    }
}
