package org.wickedsource.coderadar.security.domain;

import javax.persistence.*;
import lombok.Data;
import org.wickedsource.coderadar.user.domain.User;

/**
 * Refresh token is a JSON Web Token that is used by a client to get a new access token. In contrast
 * to access token the refresh token is persisted to have the possibility to assign the token to the
 * user entity and to revoke it.
 */
@Entity
@Table(name = "refresh_token")
@SequenceGenerator(
  name = "refresh_token_sequence",
  sequenceName = "seq_reto_id",
  allocationSize = 1
)
@Data
public class RefreshToken {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_sequence")
  private Long id;

  @Column(name = "token", nullable = false)
  private String token;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
