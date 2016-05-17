package org.wickedsource.coderadar.project.rest;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.project.domain.VcsType;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ProjectResource extends ResourceSupport {

    @NotNull
    @Length(max = 100)
    private String name;

    @NotNull
    private VcsType vcsType;

    @NotNull
    @URL
    private String vcsUrl;

    @Length(max = 100)
    private String vcsUser;

    @Length(max = 100)
    private String vcsPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VcsType getVcsType() {
        return vcsType;
    }

    public void setVcsType(VcsType vcsType) {
        this.vcsType = vcsType;
    }

    public String getVcsUrl() {
        return vcsUrl;
    }

    public void setVcsUrl(String vcsUrl) {
        this.vcsUrl = vcsUrl;
    }

    public String getVcsUser() {
        return vcsUser;
    }

    public void setVcsUser(String vcsUser) {
        this.vcsUser = vcsUser;
    }

    public String getVcsPassword() {
        return vcsPassword;
    }

    public void setVcsPassword(String vcsPassword) {
        this.vcsPassword = vcsPassword;
    }

}
