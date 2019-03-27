package org.wickedsource.coderadar.filepattern.rest;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.wickedsource.coderadar.project.domain.InclusionType;

@Data
public class FilePatternDTO {

  @NotNull private String pattern;

  @NotNull private InclusionType inclusionType;
}
