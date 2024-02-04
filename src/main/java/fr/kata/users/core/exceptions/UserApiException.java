package fr.kata.users.core.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserApiException extends RuntimeException {

    protected String code;
    protected HttpStatus status;

    public UserApiException(String code, HttpStatus status) {
        this.code = code;
        this.status = status;
    }

}
