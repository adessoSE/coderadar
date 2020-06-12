package io.reflectoring.coderadar.query.port.driver.criticalfiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFrequentlyChangedFilesCommand {
  @NotBlank private String commitHash;
  private Date startDate;
  private int frequency;
}
