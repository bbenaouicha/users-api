package fr.kata.users.core.utils;

import fr.kata.users.domain.user.UserDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Boolean.FALSE;

public class StringUtils {

    private static final Pattern EMAIL_PATTERN = Pattern
            .compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");

    private StringUtils() {
    }

    public static boolean isNotNullAndLengthIsLessThan(String value, Integer minLength) {
        return !isNullOrEmpty(value) && value.length() <= minLength;
    }

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotNullAndHasValidEmailPattern(String email) {
        if (isNotNullAndLengthIsLessThan(email, UserDto.EMAIL_MAX_LENGTH)) {
            Matcher m = EMAIL_PATTERN.matcher(email.toUpperCase());
            return m.matches();
        }
        return FALSE;
    }

}
