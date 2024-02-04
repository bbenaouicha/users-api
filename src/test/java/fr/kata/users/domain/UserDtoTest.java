package fr.kata.users.domain;

import fr.kata.users.domain.user.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDtoTest {

    @Test
    void
    GIVEN_user_dto_email_is_null_WHEN_isValidEmail_THEN_should_return_false(){
        UserDto dto = UserDto.builder()
                .email(null)
                .build();

        boolean result = dto.isValidEmail();

        Assertions.assertFalse(result);
    }

    @Test
    void
    GIVEN_user_dto_email_is_abc_WHEN_isValidEmail_THEN_should_return_false(){
        String email = "abc";
        UserDto dto = UserDto.builder()
                .email(email)
                .build();

        boolean result = dto.isValidEmail();

        Assertions.assertFalse(result);
    }


    @Test
    void
    GIVEN_user_dto_firstName_is_empty_WHEN_isValidFirstName_THEN_should_return_false(){
        String firstName = "";
        UserDto dto = UserDto.builder()
                .firstName(firstName)
                .build();

        boolean result = dto.isValidFirstName();

        Assertions.assertFalse(result);
    }

    @Test
    void
    GIVEN_user_dto_lastName_is_null_WHEN_isValidLastName_THEN_should_return_false(){
        UserDto dto = UserDto.builder()
                .lastName(null)
                .build();

        boolean result = dto.isValidLastName();

        Assertions.assertFalse(result);
    }

    @Test
    void
    GIVEN_user_dto_lastName_length_is_greater_than_50_WHEN_isValidLastName_THEN_should_return_false(){
        UserDto dto = UserDto.builder()
                .lastName("123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789")
                .build();

        boolean result = dto.isValidLastName();

        Assertions.assertFalse(result);
    }

    @Test
    void
    GIVEN_user_dto_lastName_length_is_less_than_50_WHEN_isValidLastName_THEN_should_return_true(){
        UserDto dto = UserDto.builder()
                .lastName("1234")
                .build();

        boolean result = dto.isValidLastName();

        Assertions.assertTrue(result);
    }

    @Test
    void
    GIVEN_user_dto_firstName_length_is_less_than_50_WHEN_isValidFirstName_THEN_should_return_true(){
        UserDto dto = UserDto.builder()
                .firstName("1234")
                .build();

        boolean result = dto.isValidFirstName();

        Assertions.assertTrue(result);
    }

    @Test
    void
    GIVEN_user_dto_email_is_valid_WHEN_isValidEmail_THEN_should_return_true(){
        UserDto dto = UserDto.builder()
                .email("myname@gmail.com")
                .build();

        boolean result = dto.isValidEmail();

        Assertions.assertTrue(result);
    }

}
