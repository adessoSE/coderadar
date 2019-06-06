package io.reflectoring.coderadar.projectadministration.port.driver.user.login;

import io.reflectoring.coderadar.projectadministration.port.driver.user.ValidPassword;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserCommand {
  @NotBlank private String username;

  @NotBlank
  @Length(min = 8, max = 64)
  @ValidPassword
  private String password;
}
