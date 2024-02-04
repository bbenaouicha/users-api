package fr.kata.users.domain;

import fr.kata.users.domain.user.User;
import fr.kata.users.domain.user.UserDto;
import fr.kata.users.domain.user.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void
    GIVEN_null_WHEN_toDto_THEN_Should_return_Null(){
        Assertions.assertNull(userMapper.toDto(null));
    }
    @Test
    void
    GIVEN_null_WHEN_toEntity_THEN_should_return_Null(){
        Assertions.assertNull(userMapper.toEntity(null));
    }

    @Test
    void
    GIVEN_user_WHEN_toDto_THEN_userDto_should_be_well_mapped_to_input(){
        User user= new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        UserDto userDto= userMapper.toDto(user);

        assertEquals(user.getId(),userDto.getId());
        assertEquals(user.getEmail(),userDto.getEmail());
        assertEquals(user.getFirstName(),userDto.getFirstName());
        assertEquals(user.getLastName(),userDto.getLastName());
    }

    @Test
    void
    GIVEN_UserDto_WHEN_toEntity_THEN_dtp_should_be_well_mapped_to_input(){
        UserDto userDto= new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");

        User user= userMapper.toEntity(userDto);

        assertEquals(userDto.getId(),user.getId());
        assertEquals(userDto.getEmail(),user.getEmail());
        assertEquals(userDto.getFirstName(),user.getFirstName());
        assertEquals(userDto.getLastName(),user.getLastName());
    }

}
