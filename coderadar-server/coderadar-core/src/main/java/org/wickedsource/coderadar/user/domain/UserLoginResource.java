package org.wickedsource.coderadar.user.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserLoginResource extends ResourceSupport {

  @NotNull private String username;

  @NotNull
  @Size(min = 8)
  private String password;
}
