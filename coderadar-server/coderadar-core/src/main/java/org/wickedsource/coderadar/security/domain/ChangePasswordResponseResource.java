package org.wickedsource.coderadar.security.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordResponseResource {

  // indicates, whether the password change was successful or not
  private boolean successful = true;
}
