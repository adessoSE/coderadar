package org.wickedsource.coderadar.factories.resources;

import org.wickedsource.coderadar.security.domain.PasswordChangeResource;

public class PasswordChangeResourceFactory {

  public PasswordChangeResource passwordChangeResource() {
    return new PasswordChangeResource(
        "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJjb2RlcmFkYXIiLCJleHAiOjE0ODQ1MTUzOTUsInR5cGUiOiJSRUZSRVNIIiwiaWF0IjoxNDg0NTE1NDU1LCJ1c2VySWQiOiIxIiwidXNlcm5hbWUiOiJyYWRhciJ9.zfkyc5jkPiAUEt7nU25SJxKprcPiXaiq0Q6bCJ_RrQo",
        "Coderadar4");
  }
}
