package org.wickedsource.coderadar.projectadministration.service.qualityprofile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.projectadministration.domain.QualityProfile;
import org.wickedsource.coderadar.projectadministration.port.driven.qualityprofile.LoadSingleQualityProfilePort;
import org.wickedsource.coderadar.projectadministration.port.driver.qualityprofile.LoadSingleQualityProfileCommand;
import org.wickedsource.coderadar.projectadministration.port.driver.qualityprofile.LoadSingleQualityProfileUseCase;

@Service
public class LoadSingleQualityProfileService implements LoadSingleQualityProfileUseCase {

  private final LoadSingleQualityProfilePort port;

  @Autowired
  public LoadSingleQualityProfileService(LoadSingleQualityProfilePort port) {
    this.port = port;
  }

  @Override
  public QualityProfile getQualityProfile(LoadSingleQualityProfileCommand command) {
    // TODO
    return null;
  }
}
