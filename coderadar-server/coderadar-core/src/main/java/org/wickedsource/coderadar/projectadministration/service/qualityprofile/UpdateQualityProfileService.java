package org.wickedsource.coderadar.projectadministration.service.qualityprofile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.port.driven.qualityprofile.UpdateQualityProfilePort;
import org.wickedsource.coderadar.projectadministration.port.driver.qualityprofile.UpdateQualityProfileUseCase;

@Service
public class UpdateQualityProfileService implements UpdateQualityProfileUseCase {

  private final UpdateQualityProfilePort port;

  @Autowired
  public UpdateQualityProfileService(UpdateQualityProfilePort port) {
    this.port = port;
  }
}
