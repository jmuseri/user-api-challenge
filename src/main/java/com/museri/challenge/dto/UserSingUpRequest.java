package com.museri.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude (JsonInclude.Include.NON_NULL)
public class UserSingUpRequest {

    private String name;
    @NotEmpty(message="Email must not be empty.")
    @Pattern (regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;

    @NotEmpty(message="Password must not be empty.")
    @Pattern(
            regexp = "^(?=(?:[^A-Z]*[A-Z]){1})(?=(?:\\D*\\d\\D*\\d){2})[a-zA-Z\\d]{8,12}$",
            message = "Invalid password format. The password must have exactly one uppercase letter, exactly two digits, and be 8 to 12 characters long."
    )
    private String password;
    private List<PhoneDTO> phones= new ArrayList<>();

}
