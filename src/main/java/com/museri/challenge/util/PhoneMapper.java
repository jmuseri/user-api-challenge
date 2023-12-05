package com.museri.challenge.util;

import com.museri.challenge.dto.PhoneDTO;
import com.museri.challenge.model.Phone;
import com.museri.challenge.model.User;

public class PhoneMapper {

    public static Phone mapPhone(PhoneDTO phoneDTO, User user) {
        Phone phone = new Phone();
        phone.setNumber(phoneDTO.getNumber());
        phone.setCityCode(phoneDTO.getCityCode());
        phone.setCountryCode(phoneDTO.getCountryCode());
        phone.setUser(user);
        return phone;
    }
}
