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

    public static AnalyzingStrategyResourceFactory analyzingStrategyResource() {
        return new AnalyzingStrategyResourceFactory();
    }

    public static QualityProfileResourceFactory qualityProfileResource() {
        return new QualityProfileResourceFactory();
    }

    public static UserCredentialsResourceFactory userCredentialsResource() {
        return new UserCredentialsResourceFactory();
    }

}
