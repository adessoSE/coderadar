package org.wickedsource.coderadar.qualityprofile.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface QualityProfileRepository extends CrudRepository<QualityProfile, Long> {

  List<QualityProfile> findByProjectId(Long projectId);
}
