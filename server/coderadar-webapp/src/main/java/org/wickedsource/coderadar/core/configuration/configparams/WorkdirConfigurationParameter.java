package org.wickedsource.coderadar.core.configuration.configparams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Configures the folder in which Coderadar puts files, including the local clones of VCS repositories.
 */
@Component
public class WorkdirConfigurationParameter implements ConfigurationParameter<Path> {

    public static final String NAME = "coderadar.workdir";

    private Environment environment;

    @Autowired
    public WorkdirConfigurationParameter(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Optional<Path> getValue() {
        if (envProperty() != null) {
            return Optional.of(Paths.get(envProperty()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Path> getDefaultValue() {
        return Optional.empty();
    }

    @Override
    public boolean hasFallenBackOnDefaultValue() {
        return false;
    }

    @Override
    public List<ParameterValidationError> validate() {
        if (envProperty() == null) {
            return Collections.emptyList();
        }

        try {
            File dir = Paths.get(envProperty()).toFile();
            if (dir.exists()) {
                return Collections.emptyList();
            }
            if (!dir.mkdirs()) {
                return Collections.singletonList(new ParameterValidationError(String.format("Could not create directory '%s'!", envProperty())));
            }
            return Collections.emptyList();
        } catch (SecurityException e) {
            return Collections.singletonList(new ParameterValidationError(String.format("Could not access directory '%s' due to %s", envProperty(), e.getClass().getName()), e));
        }
    }

    private String envProperty() {
        return environment.getProperty(NAME);
    }
}
