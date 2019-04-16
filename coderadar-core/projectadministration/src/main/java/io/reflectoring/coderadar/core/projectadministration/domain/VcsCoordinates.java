package io.reflectoring.coderadar.core.projectadministration.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.net.URL;
import java.util.Date;

/**
 * Coordinates to enable access to a source control system.
 */
@Embeddable
@NoArgsConstructor
@Data
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

    public VcsCoordinates(URL url) {
        this.url = url;
    }

  /*
  @Override
  public String toString() {
    ReflectionToStringBuilder builder =
        new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
    builder.setExcludeFieldNames("password");
    return builder.toString();
  }

   */
}
