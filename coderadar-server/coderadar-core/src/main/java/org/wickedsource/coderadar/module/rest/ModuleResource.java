package org.wickedsource.coderadar.module.rest;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class ModuleResource {

  private Long id;

  @NotNull private String modulePath;
}
