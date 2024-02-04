package fr.kata.users.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserMapper {

    public UserDto toDto(User user){
             if(user ==null){
                 return null;
             }
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public User toEntity(UserDto userDto){
        if(userDto ==null){
            return null;
        }
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .build();
    }

    public User newEntityFromDto(User dbUser, UserDto userDto) {
        return User.builder()
                .id(dbUser.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(dbUser.getEmail())
                .build();
    }
}
