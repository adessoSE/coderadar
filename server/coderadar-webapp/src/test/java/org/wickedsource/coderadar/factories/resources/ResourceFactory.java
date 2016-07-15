package org.wickedsource.coderadar.factories.resources;

import org.wickedsource.coderadar.factories.entities.FilePatternFactory;

public class ResourceFactory {

    public static ProjectResourceFactory projectResource() {
        return new ProjectResourceFactory();
    }

    public static FilePatternResourceFactory filePatternResource() {
        return new FilePatternResourceFactory();
    }

    public static FilePatternFactory filePattern() {
        return new FilePatternFactory();
    }

    public static AnalyzerConfigurationResourceFactory analyzerConfigurationResource() {
        return new AnalyzerConfigurationResourceFactory();
    }

}
