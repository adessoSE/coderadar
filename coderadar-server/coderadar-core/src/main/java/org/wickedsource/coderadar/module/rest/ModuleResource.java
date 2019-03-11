package org.wickedsource.coderadar.module.rest;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

@EqualsAndHashCode(callSuper = true)
@Data
public class ModuleResource extends ResourceSupport {

  @NotNull private String modulePath;
}
