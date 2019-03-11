package org.wickedsource.coderadar.analyzingjob.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AnalyzingJobResource extends ResourceSupport {

  private Date fromDate;

  @NotNull private Boolean active;

  /** Careful! Can be null! */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Boolean rescan;

  public AnalyzingJobResource(Date fromDate, boolean active) {
    this.fromDate = fromDate;
    this.active = active;
  }

  @JsonIgnore
  public boolean isRescanNullsafe() {
    return rescan != null && rescan;
  }
}
