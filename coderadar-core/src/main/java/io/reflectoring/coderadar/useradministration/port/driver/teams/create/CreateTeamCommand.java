package io.reflectoring.coderadar.useradministration.port.driver.teams.create;

import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * DTO class for creating teams.
 */
@Data
@NoArgsConstructor
public class CreateTeamCommand {
    @NotBlank private String name;
    private List<Long> userIds;
}
