package org.wickedsource.coderadar.analyzer.plugin.api;

/**
 * Default AnalyzerFilter that accepts all file names but does not accept binary files.
 */
public class DefaultFilter implements AnalyzerFilter {

    @Override
    public boolean acceptFilename(String filename) {
        return true;
    }

    @Override
    public boolean acceptBinary() {
        return false;
    }
}
