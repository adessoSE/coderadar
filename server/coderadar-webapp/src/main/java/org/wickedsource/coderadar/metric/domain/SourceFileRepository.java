package org.wickedsource.coderadar.metric.domain;

import org.springframework.data.repository.CrudRepository;

public interface SourceFileRepository extends CrudRepository<SourceFile, Long>{

    SourceFile findByCommitProjectIdAndCommitNameAndFilepath(Long projectId, String commitName, String filename);

}
