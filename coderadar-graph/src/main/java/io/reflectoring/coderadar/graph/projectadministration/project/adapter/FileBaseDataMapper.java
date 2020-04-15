package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import io.reflectoring.coderadar.graph.Mapper;
import io.reflectoring.coderadar.graph.projectadministration.domain.FileEntity;
import io.reflectoring.coderadar.projectadministration.domain.File;

public class FileBaseDataMapper implements Mapper<File, FileEntity> {

  public File mapGraphObject(FileEntity entity) {
    File file = new File();
    file.setId(entity.getId());
    file.setPath(entity.getPath());
    file.setSequenceId(entity.getSequenceId());
    file.setObjectHash(entity.getObjectHash());
    return file;
  }

  public FileEntity mapDomainObject(File file) {
    FileEntity fileEntity = new FileEntity();
    fileEntity.setPath(file.getPath());
    fileEntity.setSequenceId(file.getSequenceId());
    fileEntity.setObjectHash(file.getObjectHash());
    return fileEntity;
  }
}
