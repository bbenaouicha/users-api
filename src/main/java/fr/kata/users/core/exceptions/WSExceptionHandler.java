package fr.kata.users.core.exceptions;

import fr.kata.users.core.messages.MessagesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.LocalDateTime.now;

@ControllerAdvice
public class WSExceptionHandler {

    private final MessagesService messagesService;

    public WSExceptionHandler(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @ExceptionHandler(UserApiException.class)
    public ResponseEntity<ErrorResponse> handleWsException(UserApiException ex) {
        String code = ex.getCode();
        ErrorResponse response = ErrorResponse.builder()
                .code(code)
                .message(messagesService.value(code))
                .timestamp(now())
                .build();
        return new ResponseEntity<>(response, ex.getStatus());
    }

}
