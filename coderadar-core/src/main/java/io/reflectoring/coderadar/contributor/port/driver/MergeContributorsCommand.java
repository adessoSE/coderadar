package io.reflectoring.coderadar.contributor.port.driver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MergeContributorsCommand {
  @NotNull
  @Size(min = 2)
  private List<Long> contributorIds;

  @NotBlank private String displayName;
}
