package fr.kata.users.core.messages;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Slf4j
public class MessagesService {

    private final MessageSource messageSource;

    public MessagesService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String value(String key) {
        try {
            return messageSource.getMessage(key, null, Locale.FRENCH);
        } catch(NoSuchMessageException e) {
            log.warn("Could not find message for given key: {}", key, e);
            return key;
        }
    }

}
