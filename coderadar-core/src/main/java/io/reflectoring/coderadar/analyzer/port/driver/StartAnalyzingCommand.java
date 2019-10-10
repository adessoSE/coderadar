package io.reflectoring.coderadar.analyzer.port.driver;

import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartAnalyzingCommand {
  private Date from;
  @NotNull private Boolean rescan;
}
