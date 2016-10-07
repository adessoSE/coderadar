package org.wickedsource.coderadar.core.configuration.configparams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    private Environment environment;

    @Autowired
    public ScanIntervalConfigurationParameter(Environment environment) {
        this.environment = environment;
    }


    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Optional<Integer> getValue() {
        if (envProperty() != null) {
            return Optional.of(Integer.valueOf(envProperty()));
        } else {
            return getDefaultValue();
        }
    }

    @Override
    public Optional<Integer> getDefaultValue() {
        return Optional.of(300);
    }

    @Override
    public boolean hasFallenBackOnDefaultValue() {
        return envProperty() == null;
    }

    @Override
    public List<ParameterValidationError> validate() {
        if (envProperty() != null && !envProperty().matches("^[0-9]+$")) {
            return Collections.singletonList(new ParameterValidationError(String.format("'%s' is not a valid integer!", envProperty())));
        }
        return Collections.emptyList();
    }

    private String envProperty() {
        return environment.getProperty(NAME);
    }
}
