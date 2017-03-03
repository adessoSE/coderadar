package org.wickedsource.coderadar.factories.resources;

import org.wickedsource.coderadar.security.domain.PasswordChangeResource;

public class PasswordChangeResourceFactory {

  public PasswordChangeResource passwordChangeResource() {
    return new PasswordChangeResource("radar", "Coderadar4");
  }
}
