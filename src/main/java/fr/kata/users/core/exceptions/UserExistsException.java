package fr.kata.users.core.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class UserExistsException extends UserApiException {

    public UserExistsException() {
        super("api.error.user.exists.already", BAD_REQUEST);
    }
}
