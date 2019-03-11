package org.wickedsource.coderadar.analyzerconfig.rest;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyzerConfigurationResource extends ResourceSupport {

  @NotNull private String analyzerName;

  @NotNull private Boolean enabled;
}
