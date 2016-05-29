package org.wickedsource.coderadar.metric.domain;

import org.springframework.data.repository.CrudRepository;
import org.wickedsource.coderadar.analyzer.api.ChangeType;

import java.util.List;

public interface ScannedSourceFileRepository extends CrudRepository<ScannedSourceFile, Long> {

    List<ScannedSourceFile> findByCommitNameAndChangeTypeIn(String commitName, List<ChangeType> changeTypes);

    List<ScannedSourceFile> findByCommitName(String name);

}