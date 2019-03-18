package org.wickedsource.coderadar.commit.rest;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CommitResource {

  private String name;

  private String author;

  private Date timestamp;

  private boolean analyzed;
}
