package org.wickedsource.coderadar.analyzer.loc;

import org.wickedsource.coderadar.analyzer.api.AnalyzerFileFilter;

public class LocAnalyzerFileFilter implements AnalyzerFileFilter {

    @Override
    public boolean acceptFilename(String filename) {
        return filename.endsWith(".java");
    }

    @Override
    public boolean acceptBinary() {
        return false;
    }

}
