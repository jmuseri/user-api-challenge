package com.museri.challenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static com.museri.challenge.util.Constants.FORMATTER_DATE;

@Data
@Builder (setterPrefix = "with")
@JsonInclude (JsonInclude.Include.NON_NULL)
public class UserSingUpResponse {

    private Long id;

    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = FORMATTER_DATE)
    private LocalDateTime created;

    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = FORMATTER_DATE)
    private LocalDateTime lastLogin;

    private String token;

    private Boolean isActive;

}
