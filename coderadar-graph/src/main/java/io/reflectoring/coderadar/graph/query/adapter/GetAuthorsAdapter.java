package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.query.repository.GetAuthorsRepository;
import io.reflectoring.coderadar.query.port.driven.GetAuthorsPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetAuthorsAdapter implements GetAuthorsPort {
  private final GetAuthorsRepository getAuthorsRepository;

  public GetAuthorsAdapter(GetAuthorsRepository getAuthorsRepository) {
    this.getAuthorsRepository = getAuthorsRepository;
  }

  @Override
  public List<String> getNumberOfAuthors(Long projectId) {
    return getAuthorsRepository.getAuthors(projectId);
  }
}
