package org.wickedsource.coderadar.factories;

public class Factories {

    public static ProjectFactory project() {
        return new ProjectFactory();
    }

    public static ProjectResourceFactory projectResource() {
        return new ProjectResourceFactory();
    }

    public static JobFactory job() {
        return new JobFactory();
    }

    public static CommitFactory commit() {
        return new CommitFactory();
    }

    public static SourceFileFactory sourceFile() {
        return new SourceFileFactory();
    }

    public static FilePatternResourceFactory filePatternResource() {
        return new FilePatternResourceFactory();
    }

    public static FilePatternFactory filePattern() {
        return new FilePatternFactory();
    }

    public static AnalyzerConfigurationResourceFactory analyzerConfigurationResource(){
        return new AnalyzerConfigurationResourceFactory();
    }

    public static AnalyzerConfigurationFactory analyzerConfiguration(){
        return new AnalyzerConfigurationFactory();
    }
}
