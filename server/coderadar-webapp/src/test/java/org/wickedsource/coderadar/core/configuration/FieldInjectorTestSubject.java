package org.wickedsource.coderadar.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;

public class FieldInjectorTestSubject {

    @Autowired
    private CoderadarConfiguration injectedConfiguration;

    public CoderadarConfiguration getInjectedConfiguration() {
        return injectedConfiguration;
    }

}
