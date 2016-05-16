package org.wickedsource.coderadar.job.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScanVcsJobRepository extends CrudRepository<ScanVcsJob, Long> {

    ScanVcsJob findTop1ByProcessingStatusAndProjectIdOrderByQueuedDateDesc(ProcessingStatus status, Long projectId);

    int countByProcessingStatusInAndProjectId(List<ProcessingStatus> status, Long projectId);

}
