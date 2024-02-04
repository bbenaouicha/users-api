package fr.kata.users.core.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ErrorResponse {

    private String code;
    private String message;
    private LocalDateTime timestamp;

}
