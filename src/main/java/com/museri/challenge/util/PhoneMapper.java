package com.museri.challenge.util;

import com.museri.challenge.dto.PhoneDTO;
import com.museri.challenge.model.Phone;

public class PhoneMapper {

    public static Phone mapPhone(PhoneDTO phoneDTO) {
        Phone phone = new Phone();
        phone.setNumber(phoneDTO.getNumber());
        phone.setCityCode(phoneDTO.getCityCode());
        phone.setCountryCode(phoneDTO.getCountryCode());
        return phone;
    }
}
