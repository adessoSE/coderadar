package org.wickedsource.coderadar.analyzer.api;

public interface ProjectAnalyzerPlugin<T> {

    /**
     * Analyzes the current project.
     *
     * @param projectPath the full path of the project, starting from the VCS root.
     * @return a set of metric values calculated for the given file.
     */
    EntityMetric<T> analyzeProject(String projectPath, String basePackage) throws AnalyzerException;
}
