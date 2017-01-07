package org.wickedsource.coderadar.security.registration.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.security.domain.User;
import org.wickedsource.coderadar.security.domain.UserRegistrationDataResource;
import org.wickedsource.coderadar.security.domain.UserRepository;

@Service
public class RegistrationService {

    private final UserRepository userRepository;

    private final UserCredentialsResourceAssembler userCredentialsResourceAssembler;

    @Autowired
    public RegistrationService(UserRepository userRepository, UserCredentialsResourceAssembler userCredentialsResourceAssembler) {
        this.userRepository = userRepository;
        this.userCredentialsResourceAssembler = userCredentialsResourceAssembler;
    }

    /**
     * Creates a user and saves him in the data base.
     *
     * @param userRegistrationDataResource domain object with user information.
     * @return {@link User}, that is saved in the data base.
     */
    User register(UserRegistrationDataResource userRegistrationDataResource) {
        User user = userCredentialsResourceAssembler.toEntity(userRegistrationDataResource);
        return userRepository.save(user);
    }

    /**
     * Returns <code>true</code>, if a user with username from <code>userCredentialsResource</code> exists.
     *
     * @param userRegistrationDataResource user credentials
     * @return <code>true</code> is a user exists, falls otherwise
     */
    boolean userExists(UserRegistrationDataResource userRegistrationDataResource) {
        User user = userRepository.findByUsername(userRegistrationDataResource.getUsername());
        return user != null;
    }
}
