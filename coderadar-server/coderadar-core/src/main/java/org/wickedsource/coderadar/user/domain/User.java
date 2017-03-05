package org.wickedsource.coderadar.user.domain;

import javax.persistence.*;

/** a user of application, who has to login to access to functionality */
@Entity
@Table(
  name = "user_account",
  uniqueConstraints = {@UniqueConstraint(columnNames = "username")}
)
@SequenceGenerator(name = "user_sequence", sequenceName = "seq_user_id", allocationSize = 1)
public class User {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
  private Long id;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
}
