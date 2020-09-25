package io.reflectoring.coderadar.useradministration.port.driver.teams.create;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO class for creating teams. */
@Data
@NoArgsConstructor
public class CreateTeamCommand {
  @NotBlank private String name;
  @NotEmpty private List<Long> userIds;
}
