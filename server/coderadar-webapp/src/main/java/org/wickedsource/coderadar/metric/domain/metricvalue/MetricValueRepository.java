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

    @Query("select new org.wickedsource.coderadar.metric.domain.metricvalue.MetricValuePerCommitDTO (m.id.commit.name, m.id.metricName, sum(m.value)) from MetricValue m where m.id.commit.project.id = :projectId and m.id.metricName in (:metricNames) and m.id.commit.sequenceNumber = (select max(m2.id.commit.sequenceNumber) from MetricValue m2 where m2.id.commit.sequenceNumber<=:commitSequenceNumber and m2.id.file.identity.id = m.id.file.identity.id and m2.id.metricName = m.id.metricName) group by m.id.commit.name, m.id.metricName")
    List<MetricValuePerCommitDTO> findValuesAggregatedByCommitAndMetric(@Param("projectId") Long projectId, @Param("commitSequenceNumber") Integer commitSequenceNumber, @Param("metricNames") List<String> metricNames);

    @Query("select new org.wickedsource.coderadar.metric.domain.metricvalue.ProfileValuePerCommitDTO (qpm.profile.name, qpm.metricType, sum(mv.value)) from QualityProfileMetric qpm, MetricValue mv where qpm.name = mv.id.metricName and qpm.profile.project.id = :projectId and qpm.profile.name in (:profileNames) and mv.id.commit.sequenceNumber = (select max(m2.id.commit.sequenceNumber) from MetricValue m2 where m2.id.commit.sequenceNumber<=:commitSequenceNumber and m2.id.file.identity.id = mv.id.file.identity.id and m2.id.metricName = mv.id.metricName) group by qpm.profile.id, qpm.metricType")
//    @Query("select mv.id.commit.name, qpm.profile.name, qpm.metricType, mv.value, mv.id.commit.sequenceNumber, mv.id.file.identity.id from QualityProfileMetric qpm, MetricValue mv where qpm.name = mv.id.metricName and qpm.profile.project.id = :projectId and qpm.profile.name in (:profileNames) and mv.id.commit.sequenceNumber = (select max(m2.id.commit.sequenceNumber) from MetricValue m2 where m2.id.commit.sequenceNumber<=:commitSequenceNumber and m2.id.file.identity.id = mv.id.file.identity.id and m2.id.metricName = mv.id.metricName)")
    List<ProfileValuePerCommitDTO> findValuesAggregatedByCommitAndProfile(@Param("projectId") Long projectId, @Param("commitSequenceNumber") Integer commitSequenceNumber, @Param("profileNames") List<String> profileNames);

    @Query("select distinct m.id.metricName from MetricValue m where m.id.commit.project.id = :projectId order by m.id.metricName")
    Page<String> findMetricsInProject(@Param("projectId") Long projectId, Pageable pageable);

}
