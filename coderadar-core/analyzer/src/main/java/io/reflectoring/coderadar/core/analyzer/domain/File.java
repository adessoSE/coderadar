package io.reflectoring.coderadar.core.analyzer.domain;

import javax.persistence.*;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;

/** Represents a file in a VCS repository. */
@NodeEntity
@Data
public class File {
  private Long id;
  private String path;
}
