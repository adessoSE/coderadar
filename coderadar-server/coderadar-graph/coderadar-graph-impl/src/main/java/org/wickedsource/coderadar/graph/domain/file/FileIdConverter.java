package org.wickedsource.coderadar.graph.domain.file;

import org.neo4j.ogm.typeconversion.AttributeConverter;

public class FileIdConverter implements AttributeConverter<FileId, String> {

  @Override
  public String toGraphProperty(FileId value) {
    return value.getValue();
  }

  @Override
  public FileId toEntityAttribute(String value) {
    return FileId.from(value);
  }
}
