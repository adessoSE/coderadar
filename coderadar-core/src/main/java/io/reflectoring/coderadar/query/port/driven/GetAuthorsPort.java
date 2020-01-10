package io.reflectoring.coderadar.query.port.driven;

import java.util.List;

public interface GetAuthorsPort {
  List<String> getNumberOfAuthors(Long projectId);
}
