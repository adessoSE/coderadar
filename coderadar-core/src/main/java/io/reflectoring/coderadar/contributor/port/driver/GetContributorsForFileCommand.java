package io.reflectoring.coderadar.contributor.port.driver;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetContributorsForFileCommand {
  @NotBlank private String filename;
}
