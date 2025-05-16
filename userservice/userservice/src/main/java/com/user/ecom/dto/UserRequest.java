package com.user.ecom.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;
    private List<AddressDTO> address;
}
