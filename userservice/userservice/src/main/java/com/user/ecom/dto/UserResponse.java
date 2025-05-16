package com.user.ecom.dto;

import com.user.ecom.Entity.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private List<AddressDTO> address;
}
