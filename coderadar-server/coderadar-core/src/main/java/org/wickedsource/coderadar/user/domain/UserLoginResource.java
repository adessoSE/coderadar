package org.wickedsource.coderadar.user.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserLoginResource {

  @NotNull private String username;

  @NotNull
  @Size(min = 8)
  private String password;
}
