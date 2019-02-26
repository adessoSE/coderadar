package org.wickedsource.coderadar.module.rest;

import javax.validation.constraints.NotNull;

public class ModuleResource {

  private Long id;

  @NotNull private String modulePath;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getModulePath() {
    return modulePath;
  }

  public void setModulePath(String modulePath) {
    this.modulePath = modulePath;
  }
}
