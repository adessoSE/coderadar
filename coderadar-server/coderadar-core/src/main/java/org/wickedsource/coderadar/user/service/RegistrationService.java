package org.wickedsource.coderadar.user.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.core.common.ResourceNotFoundException;
import org.wickedsource.coderadar.user.domain.User;
import org.wickedsource.coderadar.user.domain.UserRegistrationDataResource;
import org.wickedsource.coderadar.user.domain.UserRepository;
import org.wickedsource.coderadar.user.rest.UserCredentialsResourceAssembler;

@Service
public class RegistrationService {

  private final UserRepository userRepository;

  private final UserCredentialsResourceAssembler userCredentialsResourceAssembler;

  @Autowired
  public RegistrationService(
      UserRepository userRepository,
      UserCredentialsResourceAssembler userCredentialsResourceAssembler) {
    this.userRepository = userRepository;
    this.userCredentialsResourceAssembler = userCredentialsResourceAssembler;
  }

  /**
   * Creates a user and saves him in the data base.
   *
   * @param userRegistrationDataResource domain object with user information.
   * @return {@link User}, that is saved in the data base.
   */
  public User register(UserRegistrationDataResource userRegistrationDataResource) {
    User user = userCredentialsResourceAssembler.toEntity(userRegistrationDataResource);
    return userRepository.save(user);
  }

  /**
   * Returns <code>true</code>, if a user with username from <code>userCredentialsResource</code>
   * exists.
   *
   * @param userRegistrationDataResource user credentials
   * @return <code>true</code> is a user exists, falls otherwise
   */
  public boolean userExists(UserRegistrationDataResource userRegistrationDataResource) {
    User user = userRepository.findByUsername(userRegistrationDataResource.getUsername());
    return user != null;
  }

  /**
   * This method returns the user with id <code>userId</code>. If no user with Id <code>userId
   * </code> was found, {@link ResourceNotFoundException} will be thrown.
   *
   * @param userId Id of the user to be returned
   * @return User with id <code>userId</code>
   */
  public User getUser(Long userId) {
    Optional<User> user = userRepository.findById(userId);
    if (!user.isPresent()) {
      throw new ResourceNotFoundException();
    }
    return user.get();
  }
}
