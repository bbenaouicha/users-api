package fr.kata.users.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import static fr.kata.users.core.utils.StringUtils.isNotNullAndHasValidEmailPattern;
import static fr.kata.users.core.utils.StringUtils.isNotNullAndLengthIsLessThan;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    public static final Integer FIRSTNAME_MAX_LENGTH = 50;
    public static final Integer LASTNAME_MAX_LENGTH = 50;
    public static final Integer EMAIL_MAX_LENGTH = 50;

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @JsonIgnore
    public boolean isValidFirstName() {
        return isNotNullAndLengthIsLessThan(this.firstName, FIRSTNAME_MAX_LENGTH);
    }

    @JsonIgnore
    public boolean isValidLastName() {
        return isNotNullAndLengthIsLessThan(this.lastName, FIRSTNAME_MAX_LENGTH);
    }

    @JsonIgnore
    public boolean isValidEmail() {
        return isNotNullAndHasValidEmailPattern(this.email);
    }

}
