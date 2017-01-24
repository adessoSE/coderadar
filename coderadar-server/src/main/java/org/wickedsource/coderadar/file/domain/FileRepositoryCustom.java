package org.wickedsource.coderadar.file.domain;

import java.util.List;
import org.springframework.data.repository.query.Param;

public interface FileRepositoryCustom {

  List<File> findInCommit(
      @Param("commitName") String commitName, @Param("filepaths") List<String> filepaths);
}
