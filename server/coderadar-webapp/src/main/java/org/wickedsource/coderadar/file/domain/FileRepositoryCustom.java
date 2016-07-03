package org.wickedsource.coderadar.file.domain;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepositoryCustom {

    List<File> findInCommit(@Param("commitName") String commitName, @Param("filepaths") List<String> filepaths);
}
