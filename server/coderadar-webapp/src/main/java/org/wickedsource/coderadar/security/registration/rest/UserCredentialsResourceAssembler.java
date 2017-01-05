package org.wickedsource.coderadar.security.registration.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;
import org.wickedsource.coderadar.security.domain.User;
import org.wickedsource.coderadar.security.domain.UserCredentialsResource;
import org.wickedsource.coderadar.security.service.PasswordService;

@Component
public class UserCredentialsResourceAssembler extends AbstractResourceAssembler<User, UserCredentialsResource> {

    private PasswordService passwordService;

    @Autowired
    public UserCredentialsResourceAssembler(PasswordService passwordService) {
        super(RegistrationController.class, UserCredentialsResource.class);
        this.passwordService = passwordService;
    }

    @Override
    public UserCredentialsResource toResource(User entity) {
        return null;
    }

    User toEntity(UserCredentialsResource userCredentialsResource) {
        User user = new User();
        user.setUsername(userCredentialsResource.getUsername());
        user.setPassword(passwordService.hash(userCredentialsResource.getPassword()));
        return user;
    }
}
