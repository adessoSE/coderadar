package org.wickedsource.coderadar.user.domain;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.security.ValidPassword;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegistrationDataResource extends ResourceSupport {

  @NotNull private String username;

  @ValidPassword private String password;
}
