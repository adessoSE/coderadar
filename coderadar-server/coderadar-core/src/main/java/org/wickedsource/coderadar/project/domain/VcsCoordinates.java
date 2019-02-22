package org.wickedsource.coderadar.project.domain;

import java.net.URL;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/** Coordinates to enable access to a source control system. */
@Embeddable
public class VcsCoordinates {

  @Column(name = "vcs_url", nullable = false)
  private URL url;

  @Column(name = "vcs_username")
  private String username;

  @Column(name = "vcs_password")
  private String password;

  @Column(name = "vcs_start_date")
  @Temporal(TemporalType.DATE)
  private Date startDate;

  @Column(name = "vcs_end_date")
  @Temporal(TemporalType.DATE)
  private Date endDate;

  @Column(name = "vcs_online")
  private boolean online = true;

  public VcsCoordinates() {}

  public VcsCoordinates(URL url) {
    this.url = url;
  }

  public URL getUrl() {
    return url;
  }

  public void setUrl(URL url) {
    this.url = url;
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

  public boolean isOnline() {
    return online;
  }

  public void setOnline(boolean online) {
    this.online = online;
  }

  @Override
  public String toString() {
    ReflectionToStringBuilder builder =
        new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
    builder.setExcludeFieldNames("password");
    return builder.toString();
  }
}
