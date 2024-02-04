package fr.kata.users.core.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class UserNotFoundException extends UserApiException {

    public UserNotFoundException() {
        super("api.error.user.exists.not.found", NOT_FOUND);
    }

}
