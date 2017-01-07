package org.wickedsource.coderadar.security.registration.rest;

import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;
import org.wickedsource.coderadar.security.domain.User;
import org.wickedsource.coderadar.security.domain.UserResource;

@Component
public class UserResourceAssembler  extends AbstractResourceAssembler<User, UserResource>{

    public UserResourceAssembler() {
        super(RegistrationController.class, UserResource.class);
    }

    @Override
    public UserResource toResource(User entity) {
        return new UserResource(entity.getUsername());
    }
}
