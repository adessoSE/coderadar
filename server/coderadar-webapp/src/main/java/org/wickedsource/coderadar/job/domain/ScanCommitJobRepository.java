package org.wickedsource.coderadar.job.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScanCommitJobRepository extends CrudRepository<ScanCommitJob, Long>{

    int countByProcessingStatusInAndCommitId(List<ProcessingStatus> status, Long commitId);
}
