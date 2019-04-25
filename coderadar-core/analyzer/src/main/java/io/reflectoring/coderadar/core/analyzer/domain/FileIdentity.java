package io.reflectoring.coderadar.core.analyzer.domain;

import javax.persistence.*;
import lombok.Data;

/**
 * A FileIdentity connects Files that have been renamed at some point in time. Two Files with the
 * same FileIdentity mark the same File, except that the file now has a new name. If a file has not
 * been renamed in its history, it has a unique FileIdentity.
 */
@Entity
@Table(name = "file_identity")
@SequenceGenerator(
  name = "file_identity_sequence",
  sequenceName = "seq_fiid_id",
  allocationSize = 1
)
@Data
public class FileIdentity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_identity_sequence")
  @Column(name = "id")
  private Long id;

  /*
  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
  */
}
