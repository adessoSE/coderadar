package org.wickedsource.coderadar.plugin.loc;

import org.wickedsource.coderadar.analyzer.plugin.api.AnalyzerFilter;

public class LocAnalyzerFilter implements AnalyzerFilter {

    @Override
    public boolean acceptFilename(String filename) {
        return filename.endsWith(".java");
    }

    @Override
    public boolean acceptBinary() {
        return false;
    }

}
