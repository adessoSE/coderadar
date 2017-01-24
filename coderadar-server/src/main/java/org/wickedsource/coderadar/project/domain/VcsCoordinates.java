package org.wickedsource.coderadar.project.domain;

import java.net.URL;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

  @Override
  public String toString() {
    ReflectionToStringBuilder builder =
        new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
    builder.setExcludeFieldNames("password");
    return builder.toString();
  }
}
