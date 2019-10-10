package io.reflectoring.coderadar.projectadministration.port.driver.module.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetModuleResponse {
  private Long id;
  private String path;
}
