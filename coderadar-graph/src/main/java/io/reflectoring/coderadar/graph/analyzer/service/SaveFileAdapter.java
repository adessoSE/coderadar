package io.reflectoring.coderadar.graph.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.File;
import io.reflectoring.coderadar.analyzer.port.driven.SaveFilePort;
import io.reflectoring.coderadar.graph.analyzer.repository.FileRepository;
import org.springframework.stereotype.Service;

@Service
public class SaveFileAdapter implements SaveFilePort {

  private final FileRepository fileRepository;

  public SaveFileAdapter(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  @Override
  public void save(File file) {
    this.fileRepository.save(file);
  }
}
