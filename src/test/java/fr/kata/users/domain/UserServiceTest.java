package fr.kata.users.domain;

import fr.kata.users.core.exceptions.InvalidUserAttributesException;
import fr.kata.users.core.exceptions.UserApiException;
import fr.kata.users.core.exceptions.UserExistsException;
import fr.kata.users.core.exceptions.UserNotFoundException;
import fr.kata.users.domain.user.User;
import fr.kata.users.domain.user.UserDto;
import fr.kata.users.domain.user.UserRepository;
import fr.kata.users.domain.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void
    setUp() {
        userRepository.deleteAll();
    }

    @Test
    void
    GIVEN_userDto_WHEN_createUser_THEN_User_Should_be_saved_in_db() {
        //GIVEN
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");

        //WHEN
        UserDto userSaved = userService.createUser(userDto);

        //THEN
        Assertions.assertNotNull(userSaved.getId());
    }

    @Test
    void
    GIVEN_userDto_with_null_firstName_WHEN_createUser_THEN_User_Should_throw_an_exception() {
        UserDto userDto = new UserDto();
        userDto.setFirstName(null);
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");

        InvalidUserAttributesException exception = Assertions.assertThrows(InvalidUserAttributesException.class, () -> userService.createUser(userDto));
        Assertions.assertEquals("api.error.user.invalid.attributes", exception.getCode());
    }

    @Test
    void
    GIVEN_userDto_with_null_lastName_WHEN_createUser_THEN_User_Should_throw_an_exception() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName(null);
        userDto.setEmail("john.doe@example.com");

        InvalidUserAttributesException exception = Assertions.assertThrows(InvalidUserAttributesException.class, () -> userService.createUser(userDto));
        Assertions.assertEquals("api.error.user.invalid.attributes", exception.getCode());
    }

    @Test
    void
    GIVEN_userDto_with_invalid_email_WHEN_createUser_THEN_User_Should_throw_an_exception() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe");

        InvalidUserAttributesException exception = Assertions.assertThrows(InvalidUserAttributesException.class, () -> userService.createUser(userDto));
        Assertions.assertEquals("api.error.user.invalid.attributes", exception.getCode());
    }

    @Test
    void
    GIVEN_2_users_WHEN_getAllUsers_THEN_Should_get_list_that_contains_2_users() {
        UserDto userDto1 = new UserDto();
        userDto1.setFirstName("John");
        userDto1.setLastName("Doe");
        userDto1.setEmail("john.doe@example.com");

        UserDto userDto2 = new UserDto();
        userDto2.setFirstName("Alice");
        userDto2.setLastName("Johnson");
        userDto2.setEmail("alice.johnson@example.com");
        userService.createUser(userDto1);
        userService.createUser(userDto2);

        List<UserDto> users = userService.getAllUsers();

        Assertions.assertEquals(2, users.size());
    }

    @Test
    void
    GIVEN_user_Created_WHEN_CreatedUser_THEN_should_throw_UserExistsException() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");
        userService.createUser(userDto);

        Assertions.assertThrows(UserExistsException.class, () -> userService.createUser(userDto));
    }

    @Test
    void
    GIVEN_user_created_WHEN_getUserById_THEN_should_return_the_same_user() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");
        userDto = userService.createUser(userDto);

        UserDto user = userService.getUserById(userDto.getId());

        Assertions.assertEquals(userDto.getId(), user.getId());
        Assertions.assertEquals(userDto.getEmail(), user.getEmail());
        Assertions.assertEquals(userDto.getFirstName(), user.getFirstName());
        Assertions.assertEquals(userDto.getLastName(), user.getLastName());

    }

    @Test
    void
    GIVEN_user_created_WHEN_deleteUser_THEN_should_be_deleted_from_database() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");
        userDto = userService.createUser(userDto);

        userService.deleteUser(userDto.getId());

        Assertions.assertFalse(userRepository.findById(userDto.getId()).isPresent());
    }

    @Test
    void
    GIVEN_non_existent_user_WHEN_deleteUser_THEN_should_throw_Exception_UserNotFoundException() {
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(1L);
        });
    }

    @Test
    void
    GIVEN_User_created_WHEN_updateUser_THEN_Should_update_attributes_except_email() {
        User dbUser = new User();
        dbUser.setFirstName("John");
        dbUser.setLastName("Doe");
        dbUser.setEmail("john.doe@example.com");
        userRepository.save(dbUser);

        UserDto dto = new UserDto();
        dto.setFirstName("Alice");
        dto.setLastName("Johnson");
        dto.setEmail("alice.johnson@example.com");

        UserDto user = userService.updateUser(dbUser.getId(), dto);

        Assertions.assertEquals(dbUser.getId(), user.getId());
        Assertions.assertEquals(dbUser.getEmail(), user.getEmail());
        Assertions.assertEquals(dto.getFirstName(), user.getFirstName());
        Assertions.assertEquals(dto.getLastName(), user.getLastName());
    }

    @Test
    void
    GIVEN_User_created_and_user_dto_has_invalid_firstName_WHEN_updateUser_THEN_Should_throw_an_exception() {
        User dbUser = new User();
        dbUser.setFirstName("John");
        dbUser.setLastName("Doe");
        dbUser.setEmail("john.doe@example.com");
        userRepository.save(dbUser);
        UserDto dto = new UserDto();
        dto.setFirstName(null);
        dto.setLastName("Johnson");
        dto.setEmail("alice.johnson");

        InvalidUserAttributesException exception = Assertions.assertThrows(InvalidUserAttributesException.class, () -> userService.updateUser(dbUser.getId(), dto));
        Assertions.assertEquals("api.error.user.invalid.attributes", exception.getCode());
    }

    @Test
    void
    GIVEN_User_created_and_user_dto_has_invalid_email_WHEN_updateUser_THEN_update_user_and_ignore_invalid_email() {
        User dbUser = new User();
        dbUser.setFirstName("John");
        dbUser.setLastName("Doe");
        dbUser.setEmail("john.doe@example.com");
        userRepository.save(dbUser);
        UserDto dto = new UserDto();
        dto.setFirstName("Alice");
        dto.setLastName("Johnson");
        dto.setEmail("alice.johnson");

        UserDto updatedUser = userService.updateUser(dbUser.getId(), dto);
        Assertions.assertEquals("john.doe@example.com", updatedUser.getEmail());
        Assertions.assertEquals("Alice", updatedUser.getFirstName());
        Assertions.assertEquals("Johnson", updatedUser.getLastName());
    }

    @Test
    void
    GIVEN_non_existent_user_WHEN_updateUser_THEN_Should_throw_Exception_UserNotFoundException() {
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            UserDto userDto = new UserDto();
            userDto.setFirstName("John");
            userDto.setLastName("Doe");
            userDto.setEmail("john.doe@example.com");
            userService.updateUser(1L, userDto);
        });
    }

}

