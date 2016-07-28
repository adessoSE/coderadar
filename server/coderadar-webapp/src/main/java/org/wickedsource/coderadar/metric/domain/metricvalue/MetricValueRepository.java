package org.wickedsource.coderadar.metric.domain.metricvalue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MetricValueRepository extends CrudRepository<MetricValue, Long> {

    @Query("delete from MetricValue v where v.id.commit.id in (select c.id from Commit c where c.project.id = :projectId)")
    @Modifying
    int deleteByProjectId(@Param("projectId") Long projectId);

    @Query("select new org.wickedsource.coderadar.metric.domain.metricvalue.MetricValuePerCommitDTO (m.id.commit.name, m.id.metricName, sum(m.value)) from MetricValue m where m.id.commit.project.id = :projectId and m.id.commit.name in (:commitNames) and m.id.metricName in (:metricNames) group by m.id.commit.name, m.id.metricName")
    List<MetricValuePerCommitDTO> findValuesAggregatedByCommitAndMetric(@Param("projectId") Long projectId, @Param("commitNames") List<String> commitNames, @Param("metricNames") List<String> metricNames);

    @Query("select new org.wickedsource.coderadar.metric.domain.metricvalue.ProfileValuePerCommitDTO (mv.id.commit.name, qpm.profile.name, qpm.metricType, sum(1)) from QualityProfileMetric qpm, MetricValue mv where qpm.name = mv.id.metricName and qpm.profile.project.id = :projectId and qpm.profile.name in (:profileNames) and qpm.name in (select mv.id.metricName from MetricValue mv where mv.id.commit.name in (:commitNames)) group by mv.id.commit.name, qpm.profile.id, qpm.metricType")
    List<ProfileValuePerCommitDTO> findValuesAggregatedByCommitAndProfile(@Param("projectId") Long projectId, @Param("commitNames") List<String> commitNames, @Param("profileNames") List<String> profileNames);

    @Query("select distinct m.id.metricName from MetricValue m where m.id.commit.project.id = :projectId order by m.id.metricName")
    Page<String> findMetricsInProject(@Param("projectId") Long projectId, Pageable pageable);

}
