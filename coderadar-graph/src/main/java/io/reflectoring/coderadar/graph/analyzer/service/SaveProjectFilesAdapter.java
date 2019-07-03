package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.File;
import io.reflectoring.coderadar.analyzer.port.driven.SaveFilePort;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import org.springframework.stereotype.Service;

@Service
public class SaveProjectFilesAdapter implements SaveFilePort {

  private final FileRepository fileRepository;

  public SaveProjectFilesAdapter(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  @Override
  public void save(File file) {}
}
