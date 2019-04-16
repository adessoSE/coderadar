package org.wickedsource.coderadar.analyzer.loc;

import io.reflectoring.coderadar.plugin.api.AnalyzerFileFilter;
import org.wickedsource.coderadar.analyzer.loc.profiles.Profiles;

public class LocAnalyzerFileFilter implements AnalyzerFileFilter {

    @Override
    public boolean acceptFilename(String filename) {
        return Profiles.getForFile(filename) != null;
    }
}
