package org.wickedsource.coderadar.projectadministration.port.driver.qualityprofile;

import org.wickedsource.coderadar.projectadministration.domain.QualityProfile;

public interface LoadSingleQualityProfileUseCase {
  QualityProfile getQualityProfile(LoadSingleQualityProfileCommand command);
}
