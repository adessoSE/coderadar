package io.reflectoring.coderadar.core.projectadministration.domain;

import javax.persistence.*;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;

/** a user of application, who has to login to access to functionality */
@NodeEntity
@Data
public class User {
  private Long id;
  private String username;
  private String password;
}
