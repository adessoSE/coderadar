package org.wickedsource.coderadar.security.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.wickedsource.coderadar.user.domain.User;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

  RefreshToken findByToken(String token);

  /**
   * deletes all refrech token of the user
   *
   * @param user user having refresh tokens
   */
  @Modifying
  @Query("DELETE FROM RefreshToken rt where rt.user = :user")
  int deleteByUser(@Param("user") User user);
}
