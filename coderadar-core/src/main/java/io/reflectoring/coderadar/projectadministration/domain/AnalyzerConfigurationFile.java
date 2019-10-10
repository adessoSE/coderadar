package io.reflectoring.coderadar.projectadministration.domain;

import lombok.Data;

/**
 * A configuration file for an analyzer. May have any ASCII content, depending on what kind of
 * configuration file the analyzer needs.
 */
@Data
public class AnalyzerConfigurationFile {
  private Long id;
  private byte[] fileData;
  private String contentType;
  private String fileName;
  private long sizeInBytes;
}
