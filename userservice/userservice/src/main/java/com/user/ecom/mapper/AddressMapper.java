package com.user.ecom.mapper;

import com.user.ecom.Entity.Address;
import com.user.ecom.dto.AddressDTO;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toEntity(AddressDTO adto) {

        Address ad = new Address();
        ad.setCity(adto.getCity());
        ad.setCountry(adto.getCountry());
        ad.setState(adto.getState());
        ad.setZipcode(adto.getZipcode());
        ad.setStreet(adto.getStreet());

        return ad;
    }

    public AddressDTO toDto(Address ad) {

        AddressDTO a = new AddressDTO();
        a.setCity(ad.getCity());
        a.setCountry(ad.getCountry());
        a.setStreet(ad.getStreet());
        a.setState(ad.getState());
        a.setZipcode(ad.getZipcode());

        return a;
    }
}
