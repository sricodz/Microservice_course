package com.user.ecom.mapper;

import com.user.ecom.Entity.Address;
import com.user.ecom.Entity.User;
import com.user.ecom.dto.AddressDTO;
import com.user.ecom.dto.UserRequest;
import com.user.ecom.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    @Autowired
    private AddressMapper adMapper;

    public User mapToUser(UserRequest userReq) {

        User u = new User();
        u.setEmail(userReq.getEmail());
        u.setLastName(userReq.getLastName());
        u.setFirstName(userReq.getFirstName());
        u.setPhone(userReq.getPhone());

        List<Address> address = userReq.getAddress()
                .stream()
                .map(adMapper::toEntity)
                .peek(a->a.setUser(u))
                .collect(Collectors.toList());
        u.setAddress(address);
        /*
        * Here, peek() is used to set the user (the User entity) as a reference in each Address entity.
        *This is a side-effect operation — we don’t need to modify the stream or transform its elements;
        * we simply want to set the user on each Address object.
        * */

        return u;
    }

    public UserResponse mapToUserResponse(User u) {

        UserResponse ur = new UserResponse();
        ur.setId(u.getId());
        ur.setPhone(u.getPhone());
        ur.setEmail(u.getEmail());
        ur.setLastName(u.getLastName());
        ur.setFirstName(u.getFirstName());
        ur.setRole(u.getRole());

        List<AddressDTO> ad = u.getAddress()
                .stream()
                .map(adMapper::toDto)
                .collect(Collectors.toList());
        ur.setAddress(ad);

        return ur;
    }
}
