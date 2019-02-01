package org.wickedsource.coderadar.metric.domain.finding;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueId;

public interface FindingRepository extends CrudRepository<Finding, MetricValueId> {

	@Query(
			"delete from Finding f where f.id.commit.id in (select c.id from Commit c where c.project.id = :projectId)")
	@Modifying
	int deleteByProjectId(@Param("projectId") Long projectId);
}
