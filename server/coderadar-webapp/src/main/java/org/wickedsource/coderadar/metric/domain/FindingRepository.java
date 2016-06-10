package org.wickedsource.coderadar.metric.domain;

import org.springframework.data.repository.CrudRepository;

public interface FindingRepository extends CrudRepository<Finding, MetricValueId> {
}
