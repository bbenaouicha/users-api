package fr.kata.users.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        log.info("Creating user with email {}", userDto.getEmail());
        UserDto user = userService.createUser(userDto);
        log.info("User with email {} created", userDto.getEmail());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        log.info("Searching for user with id : {}", userId);
        UserDto user = userService.getUserById(userId);
        log.info("User with id {} found", userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        log.info("Get all Users");
        List<UserDto> users = userService.getAllUsers();
        log.info("Returning {} users", users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        log.info("Deleting User with id {} ", userId);
        userService.deleteUser(userId);
        log.info("User with id {} deleted", userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        log.info("Updating user with id {}", userId);
        UserDto user = userService.updateUser(userId, userDto);
        log.info("User with id {} updated", userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
