package fr.kata.users.core.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidUserAttributesException extends UserApiException {

    public InvalidUserAttributesException() {
        super("api.error.user.invalid.attributes", HttpStatus.NOT_ACCEPTABLE);
    }
}
