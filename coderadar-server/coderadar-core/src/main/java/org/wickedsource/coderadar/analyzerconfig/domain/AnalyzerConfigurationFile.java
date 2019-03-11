package org.wickedsource.coderadar.analyzerconfig.domain;

import javax.persistence.*;
import lombok.Data;

/**
 * A configuration file for an analyzer. May have any ASCII content, depending on what kind of
 * configuration file the analyzer needs.
 */
@Entity
@Table(name = "analyzer_configuration_file")
@SequenceGenerator(
  name = "analyzer_configuration_file_sequence",
  sequenceName = "seq_acof_id",
  allocationSize = 1
)
@Data
public class AnalyzerConfigurationFile {

  @Id
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "analyzer_configuration_file_sequence"
  )
  @Column(name = "id")
  private Long id;

  @Column(name = "file_data")
  @Lob
  private byte[] fileData;

  @Column(name = "content_type")
  private String contentType;

  @Column(name = "file_name")
  private String fileName;

  @OneToOne
  @JoinColumn(name = "analyzer_configuration_id")
  private AnalyzerConfiguration analyzerConfiguration;

  @Column(name = "size_in_bytes")
  private long sizeInBytes;
}
