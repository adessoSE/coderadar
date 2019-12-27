package io.reflectoring.coderadar.graph.analyzer.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;

/** @see io.reflectoring.coderadar.analyzer.domain.AnalyzerConfigurationFile */
@NodeEntity
@Data
public class AnalyzerConfigurationFileEntity {
  private Long id;
  private byte[] fileData;
  private String contentType;
  private String fileName;
  private long sizeInBytes;
}
