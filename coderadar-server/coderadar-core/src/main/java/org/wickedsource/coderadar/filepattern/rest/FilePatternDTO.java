package org.wickedsource.coderadar.filepattern.rest;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.wickedsource.coderadar.projectadministration.domain.FileSetType;
import org.wickedsource.coderadar.projectadministration.domain.InclusionType;

@Data
public class FilePatternDTO {

  @NotNull private String pattern;

  @NotNull private InclusionType inclusionType;

  @NotNull private FileSetType fileSetType;
}
