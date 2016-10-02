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

    /**
     * Aggregates metric values by commit and metric name.
     *
     * @param projectId            ID of the project whose metrics to find.
     * @param commitSequenceNumber sequence number of the commit defining the point in time for which to retrieve the metric values.
     * @param metricNames          names of the metrics whose values to find.
     * @return list of aggregated metric values.
     */
    @Query(name = "MetricValue.findValuesAggregatedByCommitAndMetric")
    List<MetricValueDTO> findValuesAggregatedByCommitAndMetric(@Param("projectId") Long projectId, @Param("commitSequenceNumber") Integer commitSequenceNumber, @Param("metricNames") List<String> metricNames);

    /**
     * Aggregates quality profile ratings per commit and quality profile name.
     *
     * @param projectId            ID of the project whose metrics to find.
     * @param commitSequenceNumber sequence number of the commit defining the point in time for which to retrieve the metric values.
     * @param profileNames         names of the quality profiles for which to retrieve the metrics.
     * @return list of aggregated profile ratings.
     */
    @Query(name = "MetricValue.findValuesAggregatedByCommitAndProfile")
    List<ProfileValuePerCommitDTO> findValuesAggregatedByCommitAndProfile(@Param("projectId") Long projectId, @Param("commitSequenceNumber") Integer commitSequenceNumber, @Param("profileNames") List<String> profileNames);

    @Query("select distinct m.id.metricName from MetricValue m where m.id.commit.project.id = :projectId order by m.id.metricName")
    Page<String> findMetricsInProject(@Param("projectId") Long projectId, Pageable pageable);

}
