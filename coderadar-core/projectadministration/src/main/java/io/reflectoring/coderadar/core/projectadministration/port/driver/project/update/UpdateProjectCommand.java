package io.reflectoring.coderadar.core.projectadministration.port.driver.project.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProjectCommand {
  @NotNull @NotEmpty private String name;
  @NotNull private String vcsUsername;
  @NotNull private String vcsPassword;
  @NotNull @URL private String vcsUrl;
  @NotNull private Boolean vcsOnline;
  @NotNull private Date start;
  @NotNull private Date end;
}
