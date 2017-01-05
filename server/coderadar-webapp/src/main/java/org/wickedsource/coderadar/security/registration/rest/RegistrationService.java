package org.wickedsource.coderadar.security.registration.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.security.domain.User;
import org.wickedsource.coderadar.security.domain.UserCredentialsResource;
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
     * creates a user and saves him in the db
     *
     * @param userCredentialsResource domain object with user information
     */
    void register(UserCredentialsResource userCredentialsResource) {
        User user = userCredentialsResourceAssembler.toEntity(userCredentialsResource);
        userRepository.save(user);
    }

    /**
     * returns <code>true</code>, if a user with username from <code>userCredentialsResource</code> exists.
     *
     * @param userCredentialsResource user credentials
     * @return <code>true</code> is a user exists, falls otherwise
     */
    boolean existsUser(UserCredentialsResource userCredentialsResource) {
        User user = userRepository.findByUsername(userCredentialsResource.getUsername());
        return user != null;
    }
}
