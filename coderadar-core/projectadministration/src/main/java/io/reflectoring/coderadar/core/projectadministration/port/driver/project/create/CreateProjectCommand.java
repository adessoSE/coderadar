package io.reflectoring.coderadar.core.projectadministration.port.driver.project.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectCommand {
  @NotNull @NotEmpty private String name;
  @NotNull private String vcsUsername;
  @NotNull private String vcsPassword;
  @NotNull @URL private String vcsUrl;
  @NotNull private Boolean vcsOnline;
  private Date start;
  private Date end;
}
