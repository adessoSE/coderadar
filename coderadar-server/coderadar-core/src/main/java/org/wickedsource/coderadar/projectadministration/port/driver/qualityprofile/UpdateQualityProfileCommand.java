package org.wickedsource.coderadar.projectadministration.port.driver.qualityprofile;

import lombok.Value;

@Value
public class UpdateQualityProfileCommand {
    private Long id;
    private String name;
}
