package org.wickedsource.coderadar.core.configuration.configparams;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${coderadar.workdir:}")
    private Optional<String> value;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Path getValue() {
        if (value.isPresent()) {
            return Paths.get(this.value.get());
        } else {
            return null;
        }
    }

    @Override
    public Optional<Path> getDefaultValue() {
        return Optional.empty();
    }

    @Override
    public List<ParameterValidationError> validate() {
        try {
            File dir = Paths.get(this.value.get()).toFile();
            if (dir.exists()) {
                return Collections.emptyList();
            }
            if (!dir.mkdirs()) {
                return Collections.singletonList(new ParameterValidationError(String.format("Could not create directory '%s'!", this.value)));
            }
            return Collections.emptyList();
        } catch (SecurityException e) {
            return Collections.singletonList(new ParameterValidationError(String.format("Could not access directory '%s' due to %s", this.value, e.getClass().getName()), e));
        }
    }
}
