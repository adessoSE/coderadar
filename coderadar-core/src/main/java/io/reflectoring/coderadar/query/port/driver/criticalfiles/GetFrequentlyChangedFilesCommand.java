package io.reflectoring.coderadar.query.port.driver.criticalfiles;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFrequentlyChangedFilesCommand {
  @NotBlank
  @Size(min = 16)
  private String commitHash;

  private Date startDate;
  private int frequency;
}
