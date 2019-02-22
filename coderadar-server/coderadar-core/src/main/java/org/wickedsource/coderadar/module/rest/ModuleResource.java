package org.wickedsource.coderadar.module.rest;

import javax.validation.constraints.NotNull;

public class ModuleResource {

  @NotNull private String modulePath;

  public String getModulePath() {
    return modulePath;
  }

  public void setModulePath(String modulePath) {
    this.modulePath = modulePath;
  }
}
