package org.wickedsource.coderadar.job.merge;

import org.springframework.data.repository.CrudRepository;
import org.wickedsource.coderadar.job.core.ProcessingStatus;

import java.util.List;

public interface MergeLogJobRepository extends CrudRepository<MergeLogJob, Long> {

    int countByProcessingStatusInAndProjectId(List<ProcessingStatus> status, Long projectId);

}
