package org.wickedsource.coderadar.projectadministration.port.driver.analyzerconfig;

import lombok.Value;

@Value
public class UpdateAnalyzerConfigurationCommand {
    Long id;
    String analyzerName;
    Boolean enabled;
}
