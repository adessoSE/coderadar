package io.reflectoring.coderadar.query.port.driver.metrictree;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMetricTreeForCommitCommand {
  @NotBlank String commit;
  @NotNull List<String> metrics;
}
