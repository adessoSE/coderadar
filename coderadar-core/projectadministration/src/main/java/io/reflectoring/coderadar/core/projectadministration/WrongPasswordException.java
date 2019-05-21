package io.reflectoring.coderadar.core.projectadministration;

public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException(String username){
        super("Password for user " + username + " is wrong.");
    }
}
