package org.wickedsource.coderadar.analyzer.api;

/**
 * Default AnalyzerFilter that accepts all file names but does not accept binary files.
 */
public class DefaultFileFilter implements AnalyzerFileFilter {

    @Override
    public boolean acceptFilename(String filename) {
        return true;
    }

    @Override
    public boolean acceptBinary() {
        return false;
    }
}
