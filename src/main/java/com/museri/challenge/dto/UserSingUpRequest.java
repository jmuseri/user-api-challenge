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
    @NotEmpty
    @Pattern (regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;

    @NotEmpty
    @Pattern (regexp = "^(?=.*\\d.*\\d)(?=.*[A-Z])(?=.*[a-z])\\S{8,12}$", message = "Invalid password format")
    private String password;
    private List<PhoneDTO> phones= new ArrayList<>();

}
