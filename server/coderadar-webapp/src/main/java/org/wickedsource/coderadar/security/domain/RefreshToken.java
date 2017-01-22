package org.wickedsource.coderadar.security.domain;

import org.wickedsource.coderadar.user.domain.User;

import javax.persistence.*;

/**
 * Refresh token is a JSON Web Token that is used by a client to get a new access token.
 * In contrast to access token the refresh token is persisted to have the possibility to assign the token to the user entity and to revoke it.
 */
@Entity
@Table
public class RefreshToken {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String token;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
