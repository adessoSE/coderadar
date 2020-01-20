package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GetCommitsInProjectUseCase {
  List<Commit> get(Long projectId);

    Page<Commit> getPaged(Long projectId, Pageable pageRequest);
}
