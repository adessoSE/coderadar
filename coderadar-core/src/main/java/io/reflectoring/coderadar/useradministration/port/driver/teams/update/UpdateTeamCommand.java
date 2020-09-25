package io.reflectoring.coderadar.useradministration.port.driver.teams.update;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO class for updating teams. */
@Data
@NoArgsConstructor
public class UpdateTeamCommand {
  @NotBlank private String name;
  private List<Long> userIds;
}
