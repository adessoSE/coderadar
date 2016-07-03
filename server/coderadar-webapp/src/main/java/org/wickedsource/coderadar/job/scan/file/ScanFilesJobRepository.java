package org.wickedsource.coderadar.job.scan.file;

import org.springframework.data.repository.CrudRepository;
import org.wickedsource.coderadar.job.core.ProcessingStatus;

import java.util.List;

public interface ScanFilesJobRepository extends CrudRepository<ScanFilesJob, Long>{

    int countByProcessingStatusInAndCommitId(List<ProcessingStatus> status, Long commitId);

}
