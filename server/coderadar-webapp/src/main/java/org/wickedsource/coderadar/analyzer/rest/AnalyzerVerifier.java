package org.wickedsource.coderadar.analyzer.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.analyzer.api.SourceCodeFileAnalyzerPlugin;
import org.wickedsource.coderadar.analyzer.domain.AnalyzerPluginRegistry;
import org.wickedsource.coderadar.core.rest.validation.ValidationException;

@Component
public class AnalyzerVerifier {

    private AnalyzerPluginRegistry analyzerRegistry;

    @Autowired
    public AnalyzerVerifier(AnalyzerPluginRegistry analyzerRegistry) {
        this.analyzerRegistry = analyzerRegistry;
    }

    public void checkAnalyzerExistsOrThrowException(String analyzerName) {
        SourceCodeFileAnalyzerPlugin analyzer = analyzerRegistry.createAnalyzer(analyzerName);
        if (analyzer == null) {
            throw new ValidationException("analyzerName", String.format("No analyzer plugin with the name %s exists!", analyzerName));
        }
    }
}
