package org.wickedsource.coderadar.metric.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MetricValueRepository extends CrudRepository<MetricValue, Long> {

    @Query("delete from MetricValue v where v.id.commit.id in (select c.id from Commit c where c.project.id = :projectId)")
    @Modifying
    int deleteByProjectId(@Param("projectId") Long projectId);

    @Query("select new org.wickedsource.coderadar.metric.domain.MetricValueDTO (m.id.commit.name, m.id.metricName, sum(m.value)) from MetricValue m where m.id.commit.name in (:commitNames) and m.id.metricName in (:metricNames) group by m.id.commit.name, m.id.metricName")
    List<MetricValueDTO> findValuesAggregatedByCommitAndMetric(@Param("commitNames") List<String> commitNames, @Param("metricNames") List<String> metricNames);

}
