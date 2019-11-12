package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import io.reflectoring.coderadar.vcs.port.driven.GetRawCommitContentPort;
import io.reflectoring.coderadar.vcs.port.driver.GetCommitRawContentUseCase;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetRawCommitContentService implements GetCommitRawContentUseCase {
  private final GetRawCommitContentPort getRawCommitContentPort;

  @Autowired
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
}
