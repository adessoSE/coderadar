package io.reflectoring.coderadar.core.projectadministration.port.driver.user.load;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoadUserResponse {
  private Long id;
  private String username;
}
