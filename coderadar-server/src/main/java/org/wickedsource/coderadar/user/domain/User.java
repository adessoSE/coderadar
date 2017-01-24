package org.wickedsource.coderadar.user.domain;

import javax.persistence.*;

/** a user of application, who has to login to access to functionality */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class User {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
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
