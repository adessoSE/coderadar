package org.wickedsource.coderadar.projectadministration.service.qualityprofile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.port.driven.qualityprofile.CreateQualityProfilePort;
import org.wickedsource.coderadar.projectadministration.port.driver.qualityprofile.CreateQualityProfileCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.qualityprofile.CreateQualityProfileUseCase;

@Service
public class CreateQualityProfileService implements CreateQualityProfileUseCase {

  private final CreateQualityProfilePort port;

  @Autowired
  public CreateQualityProfileService(CreateQualityProfilePort port) {
    this.port = port;
  }

  @Override
  public Long createQualityProfile(CreateQualityProfileCommand command) {
    // TODO: Quality profiles will be added in a later version.
    return null;
  }
}
