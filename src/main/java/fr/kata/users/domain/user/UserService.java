package fr.kata.users.domain.user;

import fr.kata.users.core.exceptions.InvalidUserAttributesException;
import fr.kata.users.core.exceptions.UserExistsException;
import fr.kata.users.core.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto createUser(UserDto userDto) {
        checkUserAttributesValidationForCreation(userDto);
        checkUserExists(userDto);
        User user = userMapper.toEntity(userDto);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    private void checkUserExists(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            log.error("User with email {} already exists", userDto.getEmail());
            throw new UserExistsException();
        }
    }

    public UserDto getUserById(Long id) {
        User user = findUserById(id);
        return userMapper.toDto(user);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    private void checkUserAttributesValidationForCreation(UserDto user) {
        if (user.isValidFirstName() &&
                user.isValidLastName() &&
                user.isValidEmail()) {
            return;
        }
        throw new InvalidUserAttributesException();
    }


    public List<UserDto> getAllUsers() {
        return userRepository
                .findAllByOrderByIdDesc()
                .stream()
                .map(userMapper::toDto)
                .collect(toList());
    }

    public void deleteUser(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        checkUserAttributesValidationForUpdate(userDto);
        User dbUser = findUserById(id);
        User userToUpdate = userMapper.newEntityFromDto(dbUser, userDto);
        userToUpdate = userRepository.save(userToUpdate);
        return userMapper.toDto(userToUpdate);
    }

    private void checkUserAttributesValidationForUpdate(UserDto user) {
        if (user.isValidFirstName() &&
                user.isValidLastName()) {
            return;
        }
        throw new InvalidUserAttributesException();
    }

}
