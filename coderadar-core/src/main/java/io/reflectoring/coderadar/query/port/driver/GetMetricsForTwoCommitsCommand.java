package io.reflectoring.coderadar.query.port.driver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMetricsForTwoCommitsCommand {
  @NotNull @NotEmpty String commit1;
  @NotNull @NotEmpty String commit2;
  @NotNull List<String> metrics;
}
