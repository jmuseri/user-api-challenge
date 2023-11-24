package com.museri.challenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static com.museri.challenge.util.Constants.FORMATTER_DATE;

@Data
@Builder (setterPrefix = "with")
@JsonInclude (JsonInclude.Include.NON_NULL)
public class UserLoggedResponse {


    private Long id;

    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = FORMATTER_DATE)
    private LocalDateTime created;

    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = FORMATTER_DATE)
    private LocalDateTime lastLogin;

    private String token;

    private Boolean isActive;

    private String name;

    private String email;

    private String password;

    private List<PhoneDTO> phones;

}
