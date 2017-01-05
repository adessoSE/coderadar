package org.wickedsource.coderadar.security.authentication.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.security.domain.UserCredentialsResource;

import java.util.HashMap;
import java.util.Map;

@Controller
@Transactional
@RequestMapping(path = "/user/login")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    private static Map<String, String> dbMock = new HashMap<>();
    static {
        dbMock.put("john", "smith");
        dbMock.put("stephen", "queen");
    }

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public AuthenticationTokenResource login(@RequestBody UserCredentialsResource userCredentialsResource) {
        if (dbMock.get(userCredentialsResource.getUsername()).equals(userCredentialsResource.getPassword())) {
            String token = authenticationService.createToken(userCredentialsResource);
            return new AuthenticationTokenResource(token);
        }
        // TODO 401-Error
        throw new IllegalArgumentException("access denied");
    }
}
