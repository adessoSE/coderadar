package org.wickedsource.coderadar.qualityprofile.domain;

import org.springframework.data.repository.CrudRepository;

public interface QualityProfileMetricRepository extends CrudRepository<QualityProfileMetric, Long> {

    void deleteByProfileId(Long id);

}
