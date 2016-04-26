package org.wickedsource.coderadar.analyzer;

import org.wickedsource.coderadar.analyzer.match.FileMatchingPattern;

import java.util.Properties;

public class AnalyzerConfiguration {

    private Properties pluginProperties = new Properties();

    private FileMatchingPattern sourceCodeFiles;

    private FileMatchingPattern findbugsReports;

    public FileMatchingPattern getSourceCodeFiles() {
        return sourceCodeFiles;
    }

    public void setSourceCodeFiles(FileMatchingPattern sourceCodeFiles) {
        this.sourceCodeFiles = sourceCodeFiles;
    }

    public FileMatchingPattern getFindbugsReports() {
        return findbugsReports;
    }

    public void setFindbugsReports(FileMatchingPattern findbugsReports) {
        this.findbugsReports = findbugsReports;
    }

    public Properties getPluginProperties() {
        return pluginProperties;
    }

    public void setPluginProperties(Properties pluginProperties) {
        this.pluginProperties = pluginProperties;
    }
}
