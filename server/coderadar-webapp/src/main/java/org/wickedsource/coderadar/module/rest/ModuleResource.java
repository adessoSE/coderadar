package org.wickedsource.coderadar.module.rest;

import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

public class ModuleResource extends ResourceSupport {

    @NotNull
    private String modulePath;

    public String getModulePath() {
        return modulePath;
    }

    public void setModulePath(String modulePath) {
        this.modulePath = modulePath;
    }
}
