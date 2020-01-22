package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.projectadministration.domain.File;
import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import io.reflectoring.coderadar.vcs.port.driven.GetRawCommitContentPort;
import io.reflectoring.coderadar.vcs.port.driver.GetCommitRawContentUseCase;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class GetRawCommitContentService implements GetCommitRawContentUseCase {
  private final GetRawCommitContentPort getRawCommitContentPort;

  public GetRawCommitContentService(GetRawCommitContentPort getRawCommitContentPort) {
    this.getRawCommitContentPort = getRawCommitContentPort;
  }

  @Override
  public byte[] getCommitContent(String projectRoot, String filepath, String name)
      throws UnableToGetCommitContentException {
    return getRawCommitContentPort.getCommitContent(projectRoot, filepath, name);
  }

  @Override
  public HashMap<String, byte[]> getCommitContentBulk(
      String projectRoot, List<String> filepaths, String name)
      throws UnableToGetCommitContentException {
    return getRawCommitContentPort.getCommitContentBulk(projectRoot, filepaths, name);
  }

  @Override
  public HashMap<File, byte[]> getCommitContentBulkWithFiles(
      String projectRoot, List<File> files, String name) throws UnableToGetCommitContentException {
    return getRawCommitContentPort.getCommitContentBulkWithFiles(projectRoot, files, name);
  }
}
