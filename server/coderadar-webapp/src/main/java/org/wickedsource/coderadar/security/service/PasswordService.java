package org.wickedsource.coderadar.security.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for working with passwords, for example hashing or verification.
 */
@Service
public class PasswordService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    /**
     * hash the password with "bcrypt"
     *
     * @param password the password to be hashed
     * @return hashed password as hexadecimal
     */
    public String hash(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * checks, if the passwords match
     *
     * @param rawPassword     the password to be verified
     * @param encodedPassword encoded the password as hexadecimal
     * @return <code>true</code>, if the password match, false otherwise
     */
    boolean verify(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
