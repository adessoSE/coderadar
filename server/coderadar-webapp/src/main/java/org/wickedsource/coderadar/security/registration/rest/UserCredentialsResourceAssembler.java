package org.wickedsource.coderadar.security.registration.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;
import org.wickedsource.coderadar.security.domain.User;
import org.wickedsource.coderadar.security.domain.UserRegistrationDataResource;
import org.wickedsource.coderadar.security.service.PasswordService;

@Component
public class UserCredentialsResourceAssembler extends AbstractResourceAssembler<User, UserRegistrationDataResource> {

    private PasswordService passwordService;

    @Autowired
    public UserCredentialsResourceAssembler(PasswordService passwordService) {
        super(RegistrationController.class, UserRegistrationDataResource.class);
        this.passwordService = passwordService;
    }

    @Override
    public UserRegistrationDataResource toResource(User entity) {
        return null;
    }

    User toEntity(UserRegistrationDataResource userRegistrationDataResource) {
        User user = new User();
        user.setUsername(userRegistrationDataResource.getUsername());
        user.setPassword(passwordService.hash(userRegistrationDataResource.getPassword()));
        return user;
    }
}
