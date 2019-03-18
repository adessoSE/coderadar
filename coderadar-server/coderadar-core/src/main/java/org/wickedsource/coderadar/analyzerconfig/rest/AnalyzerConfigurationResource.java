package org.wickedsource.coderadar.analyzerconfig.rest;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyzerConfigurationResource {

  private Long id;

  @NotNull private String analyzerName;

  @NotNull private Boolean enabled;
}
