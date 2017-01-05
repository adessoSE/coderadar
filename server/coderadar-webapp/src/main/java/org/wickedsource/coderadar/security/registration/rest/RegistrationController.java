package org.wickedsource.coderadar.security.registration.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.security.domain.UserCredentialsResource;

@Controller
@Transactional
@RequestMapping(path = "/user/register")
public class RegistrationController {

    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody UserCredentialsResource userCredentialsResource) {
        if (registrationService.existsUser(userCredentialsResource)) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        registrationService.register(userCredentialsResource);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
