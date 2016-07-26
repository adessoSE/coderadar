package org.wickedsource.coderadar.metric.domain.qualityprofile;

import org.springframework.data.repository.CrudRepository;

public interface QualityProfileMetricRepository extends CrudRepository<QualityProfileMetric, Long> {

    void deleteByProfileId(Long id);

}
