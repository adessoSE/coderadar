package io.reflectoring.coderadar.query.port.driver;

import java.util.List;

public interface GetAuthorsUseCase {
  List<String> getNumberOfAuthors(Long projectId);
}
