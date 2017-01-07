package org.wickedsource.coderadar.security.registration.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.core.rest.validation.RegistrationException;
import org.wickedsource.coderadar.security.domain.User;
import org.wickedsource.coderadar.security.domain.UserRegistrationDataResource;
import org.wickedsource.coderadar.security.domain.UserResource;

import javax.validation.Valid;

@Controller
@Transactional
@RequestMapping(path = "/user/register")
public class RegistrationController {

    private final RegistrationService registrationService;

    private final UserResourceAssembler userResourceAssembler;


    @Autowired
    public RegistrationController(RegistrationService registrationService, UserResourceAssembler userResourceAssembler) {
        this.registrationService = registrationService;
        this.userResourceAssembler = userResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserResource> register(@Valid @RequestBody UserRegistrationDataResource userRegistrationDataResource) {
        if (registrationService.userExists(userRegistrationDataResource)) {
            throw new RegistrationException(userRegistrationDataResource.getUsername());
        }
        User registeredUser = registrationService.register(userRegistrationDataResource);
        UserResource userResource = userResourceAssembler.toResource(registeredUser);
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }
}
