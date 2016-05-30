package org.wickedsource.coderadar.job.scan.commit;

import org.springframework.data.repository.CrudRepository;
import org.wickedsource.coderadar.job.core.ProcessingStatus;

import java.util.List;

public interface ScanCommitsJobRepository extends CrudRepository<ScanCommitsJob, Long> {

    ScanCommitsJob findTop1ByProcessingStatusAndProjectIdOrderByQueuedDateDesc(ProcessingStatus status, Long projectId);

    int countByProcessingStatusInAndProjectId(List<ProcessingStatus> status, Long projectId);

}
