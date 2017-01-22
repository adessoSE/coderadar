package org.wickedsource.coderadar.analyzer.domain;

import javax.persistence.*;

/**
 * A configuration file for an analyzer. May have any ASCII content, depending on what kind of
 * configuration file the analyzer needs.
 */
@Entity
@Table
public class AnalyzerConfigurationFile {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column @Lob private byte[] fileData;

  @Column private String contentType;

  @Column private String fileName;

  @OneToOne private AnalyzerConfiguration analyzerConfiguration;

  @Column private long sizeInBytes;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public byte[] getFileData() {
    return fileData;
  }

  public void setFileData(byte[] fileData) {
    this.fileData = fileData;
  }

  public AnalyzerConfiguration getAnalyzerConfiguration() {
    return analyzerConfiguration;
  }

  public void setAnalyzerConfiguration(AnalyzerConfiguration analyzerConfiguration) {
    this.analyzerConfiguration = analyzerConfiguration;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public long getSizeInBytes() {
    return sizeInBytes;
  }

  public void setSizeInBytes(long sizeInBytes) {
    this.sizeInBytes = sizeInBytes;
  }
}
