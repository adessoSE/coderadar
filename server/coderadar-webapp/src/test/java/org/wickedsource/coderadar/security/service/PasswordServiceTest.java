package org.wickedsource.coderadar.security.service;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordServiceTest {

    private PasswordService passwordService = new PasswordService();

    @Test
    public void hash() throws Exception {
        String encodedPassword = passwordService.hash("myPassword");
        boolean result = passwordService.verify("myPassword", encodedPassword);
        assertThat(result).isTrue();
    }

}