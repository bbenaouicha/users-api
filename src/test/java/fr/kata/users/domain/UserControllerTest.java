package fr.kata.users.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.kata.users.core.exceptions.UserNotFoundException;
import fr.kata.users.domain.user.UserDto;
import fr.kata.users.domain.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserService userService;

    @Test
    void
    GIVEN_idUser_WHEN_getUserById_THEN_Should_return_user_with_http_status_ok() throws Exception {
        UserDto userDto= new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");
        Mockito.when(userService.getUserById(Mockito.anyLong())).thenReturn(userDto);


        mockMvc.perform(get("/users/" + userDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(userDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDto.getLastName()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(status().isOk());
    }

    @Test
    void
    GIVEN_2_Users_WHEN_getAllUsers_THEN_Should_return_list_of_2_users() throws Exception {
        UserDto userDto1= new UserDto();
        userDto1.setFirstName("John");
        userDto1.setLastName("Doe");
        userDto1.setEmail("john.doe@example.com");

        UserDto userDto2= new UserDto();
        userDto2.setFirstName("Alice");
        userDto2.setLastName("Johnson");
        userDto2.setEmail("alice.johnson@example.com");
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(userDto1,userDto2));


        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(status().isOk());
    }

    @Test
    void GIVEN_validUserDto_WHEN_createUser_THEN_shouldReturnCreatedUserWithHttpStatusCreated() throws Exception {
        // Given
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");

        Mockito.when(userService.createUser(Mockito.any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(userDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDto.getLastName()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()));
    }

    @Test
    void
    GIVEN_validUserId_WHEN_deleteUser_THEN_shouldReturnHttpStatusOk() throws Exception {
        Long userId = 1L;

        mockMvc.perform(delete("/users/{userId}", userId))
                .andExpect(status().isOk());
    }
    @Test
    void
    GIVEN_validUserIdAndUserDto_WHEN_updateUser_THEN_shouldReturnUpdatedUserWithHttpStatusOk() throws Exception {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setFirstName("UpdatedFirstName");
        userDto.setLastName("UpdatedLastName");
        userDto.setEmail("updated.email@example.com");

        Mockito.when(userService.updateUser(Mockito.anyLong(), Mockito.any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(put("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(userDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDto.getLastName()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()));
    }

    @Test
    void
    GIVEN_non_existent_user_when_getUserById_THEN_should_http_status_not_found() throws Exception {
        long userId = 1L;

        Mockito.when(userService.getUserById(Mockito.anyLong())).thenThrow(new UserNotFoundException());

        mockMvc.perform(get("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void
    GIVEN_non_existent_user_when_deleteById_THEN_should_http_status_not_found() throws Exception {
        Long userId = 1L;

        Mockito.doThrow(new UserNotFoundException()).when(userService).deleteUser(userId);

        mockMvc.perform(delete("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void
    GIVEN_non_existent_user_when_updateUserTHEN_should_http_status_not_found() throws Exception {
        long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setFirstName("UpdatedFirstName");
        userDto.setLastName("UpdatedLastName");
        userDto.setEmail("updated.email@example.com");
        Mockito.when(userService.updateUser(Mockito.anyLong(),Mockito.any())).thenThrow(new UserNotFoundException());

        mockMvc.perform(put("/users/" + userId)
                        .content(new ObjectMapper().writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
