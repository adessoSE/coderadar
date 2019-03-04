package org.wickedsource.coderadar.commit.rest;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommitResource extends ResourceSupport {

  private String name;

  private String author;

  private Date timestamp;

  private boolean analyzed;
}
