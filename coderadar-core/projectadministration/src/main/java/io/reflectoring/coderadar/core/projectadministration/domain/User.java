package io.reflectoring.coderadar.core.projectadministration.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * a user of application, who has to login to access to functionality
 */
@Entity
@Table(
        name = "user_account",
        uniqueConstraints = {@UniqueConstraint(columnNames = "username")}
)
@SequenceGenerator(name = "user_sequence", sequenceName = "seq_user_id", allocationSize = 1)
@Data
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;
}
