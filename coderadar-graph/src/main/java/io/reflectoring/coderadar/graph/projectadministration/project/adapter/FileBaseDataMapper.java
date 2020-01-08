package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import io.reflectoring.coderadar.projectadministration.domain.File;

public class FileBaseDataMapper {

  private FileBaseDataMapper() {}

  public static File mapFileEntity(FileEntity entity) {
    File file = new File();
    file.setId(entity.getId());
    file.setPath(entity.getPath());
    return file;
  }

  public static FileEntity mapFile(File file) {
    FileEntity fileEntity = new FileEntity();
    fileEntity.setPath(file.getPath());
    return fileEntity;
  }
}
