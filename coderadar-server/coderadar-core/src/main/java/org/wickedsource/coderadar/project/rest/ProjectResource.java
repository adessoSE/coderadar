package org.wickedsource.coderadar.project.rest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.hateoas.ResourceSupport;

public class ProjectResource extends ResourceSupport {

  @NotNull
  @Length(max = 100)
  private String name;

  @NotNull @URL private String vcsUrl;

  @Length(max = 100)
  private String vcsUser;

  @Length(max = 100)
  private String vcsPassword;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate startDate;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate endDate;

  private boolean vcsOnline = true;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public boolean isVcsOnline() {
    return vcsOnline;
  }

  public void setVcsOnline(boolean vcsOnline) {
    this.vcsOnline = vcsOnline;
  }
}
