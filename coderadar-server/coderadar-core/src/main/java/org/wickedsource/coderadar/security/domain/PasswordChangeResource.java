package org.wickedsource.coderadar.security.domain;

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
public class PasswordChangeResource extends ResourceSupport {

  @NotNull private String refreshToken;

  @ValidPassword private String newPassword;
}
