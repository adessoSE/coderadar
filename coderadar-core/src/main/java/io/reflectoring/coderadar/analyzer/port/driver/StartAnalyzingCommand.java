package io.reflectoring.coderadar.analyzer.port.driver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartAnalyzingCommand {
  private Date from;
  @NotNull private Boolean rescan;
}
