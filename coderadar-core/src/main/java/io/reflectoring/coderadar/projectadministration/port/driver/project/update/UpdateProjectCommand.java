package io.reflectoring.coderadar.projectadministration.port.driver.project.update;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProjectCommand {
  @NotBlank private String name;
  private String vcsUsername;
  private String vcsPassword;
  @NotNull @URL private String vcsUrl; // TODO: Should the user be able to change the url???
  @NotNull private Boolean vcsOnline;
  private Date startDate;
  private Date endDate;
}
