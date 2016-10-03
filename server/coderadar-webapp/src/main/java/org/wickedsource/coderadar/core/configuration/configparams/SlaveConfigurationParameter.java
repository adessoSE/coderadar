package org.wickedsource.coderadar.core.configuration.configparams;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Determines if the Coderadar application is configured as a slave node in a coderadar cluster.
 * A node can be configured as master and slave simultaneously. However, there must only be a single master
 * in a cluster.
 */
@Component
public class SlaveConfigurationParameter implements ConfigurationParameter<Boolean> {

    public static final String NAME = "coderadar.slave";

    @Value("${coderadar.slave:}")
    private Optional<Boolean> value;


    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Boolean getValue() {
        if (value.isPresent()) {
            return value.get();
        } else {
            return null;
        }
    }

    @Override
    public Optional<Boolean> getDefaultValue() {
        return Optional.empty();
    }

    @Override
    public List<ParameterValidationError> validate() {
        return Collections.emptyList();
    }
}
