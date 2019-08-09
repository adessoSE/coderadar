package io.reflectoring.coderadar.query.port.driver;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMetricsForCommitCommand {
  @NotNull @NotEmpty String commit;
  @NotNull List<String> metrics;
}
