package org.wickedsource.coderadar.job.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FileIdentityMergeJobRepository extends CrudRepository<FileIdentityMergeJob, Long> {

    int countByProcessingStatusInAndProjectId(List<ProcessingStatus> status, Long projectId);

}
