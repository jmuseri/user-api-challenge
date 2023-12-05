package com.museri.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder (setterPrefix = "with")
@ToString
@JsonInclude (JsonInclude.Include.NON_NULL)
public class PhoneDTO {

    @Pattern (regexp = "\\d{9,15}", message = "Invalid phone number format")
    private String number;

    @Pattern (regexp = "\\d{1,3}", message = "Invalid city code format")
    private String cityCode;

    @Pattern (regexp = "\\d{1,3}", message = "Invalid country code format")
    private String countryCode;
}
