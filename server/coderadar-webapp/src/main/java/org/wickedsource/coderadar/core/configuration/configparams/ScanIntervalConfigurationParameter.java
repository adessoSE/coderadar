package org.wickedsource.coderadar.core.configuration.configparams;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Configures the number of seconds to wait before scanning a project's remote VCS again for new commits.
 */
@Component
public class ScanIntervalConfigurationParameter implements ConfigurationParameter<Integer> {

    public static final String NAME = "coderadar.scanIntervalInSeconds";

    @Value("${coderadar.scanIntervalInSeconds:}")
    private Optional<Integer> value;


    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Integer getValue() {
        if (value.isPresent()) {
            return value.get();
        } else {
            return getDefaultValue().get();
        }
    }

    @Override
    public Optional<Integer> getDefaultValue() {
        return Optional.of(300);
    }

    @Override
    public List<ParameterValidationError> validate() {
        return Collections.emptyList();
    }
}
