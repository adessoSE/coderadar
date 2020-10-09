package io.reflectoring.coderadar.query.port.driver.commitmetrics;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMetricValuesOfCommitCommand {
  @NotBlank
  @Size(min = 16)
  String commit;

  @NotNull List<String> metrics;
}
