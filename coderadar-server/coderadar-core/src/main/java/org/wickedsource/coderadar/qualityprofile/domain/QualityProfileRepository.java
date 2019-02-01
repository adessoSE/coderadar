package org.wickedsource.coderadar.qualityprofile.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface QualityProfileRepository extends CrudRepository<QualityProfile, Long> {

	Page<QualityProfile> findByProjectId(Long projectId, Pageable pageable);
}
