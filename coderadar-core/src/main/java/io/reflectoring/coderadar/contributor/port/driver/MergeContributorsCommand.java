package io.reflectoring.coderadar.contributor.port.driver;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MergeContributorsCommand {
  @NotNull
  @Size(min = 2)
  private List<Long> contributorIds;

  @NotBlank private String displayName;
}
