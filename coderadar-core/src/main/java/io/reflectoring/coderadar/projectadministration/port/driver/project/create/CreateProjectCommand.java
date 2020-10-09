package io.reflectoring.coderadar.projectadministration.port.driver.project.create;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectCommand {
  @NotBlank private String name;
  private String vcsUsername;
  private String vcsPassword;
  @NotNull @URL private String vcsUrl;
  private boolean vcsOnline;
  private @Past Date startDate;
  @NotBlank private String defaultBranch;
}
