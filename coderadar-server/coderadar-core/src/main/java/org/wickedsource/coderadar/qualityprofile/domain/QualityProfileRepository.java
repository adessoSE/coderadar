package org.wickedsource.coderadar.qualityprofile.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.wickedsource.coderadar.projectadministration.domain.QualityProfile;

public interface QualityProfileRepository extends CrudRepository<QualityProfile, Long> {

  List<QualityProfile> findByProjectId(Long projectId);
}
