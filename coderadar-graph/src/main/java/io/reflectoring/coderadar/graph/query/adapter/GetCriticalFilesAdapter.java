package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import io.reflectoring.coderadar.query.port.driven.GetCriticalFilesPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetCriticalFilesAdapter implements GetCriticalFilesPort {
  private final FileRepository fileRepository;

  public GetCriticalFilesAdapter(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  @Override
  public List<String> getCriticalFiles(Long projectId) {
    return fileRepository.getCriticalFiles(projectId);
  }
}
