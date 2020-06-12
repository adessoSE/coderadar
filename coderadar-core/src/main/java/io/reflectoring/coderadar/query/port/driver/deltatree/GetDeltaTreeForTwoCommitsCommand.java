package io.reflectoring.coderadar.query.port.driver.deltatree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDeltaTreeForTwoCommitsCommand {
  @NotBlank String commit1;
  @NotBlank String commit2;
  @NotNull List<String> metrics;
}
