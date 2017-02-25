package org.wickedsource.coderadar.project.domain;

import java.net.URL;
import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/** Coordinates to enable access to a source control system. */
@Embeddable
public class VcsCoordinates {

  @Column(nullable = false)
  private URL url;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private VcsType type;

  @Column private String username;

  @Column private String password;

  @Column
  @Temporal(TemporalType.DATE)
  private Date startDate;

  @Column
  @Temporal(TemporalType.DATE)
  private Date endDate;

  public VcsCoordinates() {}

  public VcsCoordinates(URL url, VcsType type) {
    this.url = url;
    this.type = type;
  }

  public URL getUrl() {
    return url;
  }

  public void setUrl(URL url) {
    this.url = url;
  }

  public VcsType getType() {
    return type;
  }

  public void setType(VcsType type) {
    this.type = type;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  @Override
  public String toString() {
    ReflectionToStringBuilder builder =
        new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
    builder.setExcludeFieldNames("password");
    return builder.toString();
  }
}
