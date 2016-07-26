package org.wickedsource.coderadar.metric.domain.qualityprofile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface QualityProfileRepository extends CrudRepository<QualityProfile, Long> {

    Page<QualityProfile> findByProjectId(Long projectId, Pageable pageable);

}
