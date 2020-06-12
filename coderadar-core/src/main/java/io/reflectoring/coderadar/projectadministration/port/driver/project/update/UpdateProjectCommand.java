package io.reflectoring.coderadar.projectadministration.port.driver.project.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProjectCommand {
  @NotBlank private String name;
  private String vcsUsername;
  private String vcsPassword;
  @NotNull @URL private String vcsUrl;
  private boolean vcsOnline;
  private Date startDate;
}
