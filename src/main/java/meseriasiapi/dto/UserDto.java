package meseriasiapi.dto;

import lombok.Builder;
import lombok.Getter;
import meseriasiapi.domain.Role;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class UserDto {

    private UUID id;

    private String email;

    private String password;

    private Role role;

    private String phone;

    private double rating;

    private LocalDateTime creationDate;

    private String firstName;

    private String lastName;

    private String description;

    private Integer yearsOfExperience;

    private Integer age;

    private String categoriesOfInterest;

}
