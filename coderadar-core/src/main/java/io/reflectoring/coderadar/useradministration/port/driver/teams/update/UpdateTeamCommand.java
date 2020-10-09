package io.reflectoring.coderadar.useradministration.port.driver.teams.update;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO class for updating teams. */
@Data
@NoArgsConstructor
public class UpdateTeamCommand {
  @NotBlank private String name;
  @NotEmpty private List<Long> userIds;
}
