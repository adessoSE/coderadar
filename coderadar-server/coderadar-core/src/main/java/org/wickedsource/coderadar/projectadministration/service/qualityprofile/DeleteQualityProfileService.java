package org.wickedsource.coderadar.projectadministration.service.qualityprofile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.port.driven.qualityprofile.DeleteQualityProfilePort;
import org.wickedsource.coderadar.projectadministration.port.driver.qualityprofile.DeleteQualityProfileCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.qualityprofile.DeleteQualityProfileUseCase;

@Service
public class DeleteQualityProfileService implements DeleteQualityProfileUseCase {

  private final DeleteQualityProfilePort port;

  @Autowired
  public DeleteQualityProfileService(DeleteQualityProfilePort port) {
    this.port = port;
  }

  @Override
  public void deleteQualityProfile(DeleteQualityProfileCommand command) {
    // TODO: Quality profiles will be added in a later version.
  }
}
