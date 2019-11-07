package io.reflectoring.coderadar.useradministration.port.driver.register;

import io.reflectoring.coderadar.useradministration.port.driver.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserCommand {
  @NotBlank private String username;

  @NotBlank
  @Length(min = 8, max = 64)
  @ValidPassword
  private String password;
}
