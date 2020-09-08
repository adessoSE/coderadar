package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import io.reflectoring.coderadar.graph.Mapper;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import io.reflectoring.coderadar.projectadministration.domain.File;

public class FileBaseDataMapper implements Mapper<File, FileEntity> {

  public File mapGraphObject(FileEntity entity) {
    return new File(entity.getId(), entity.getPath());
  }

  public FileEntity mapDomainObject(File file) {
    return new FileEntity(file.getPath());
  }
}
