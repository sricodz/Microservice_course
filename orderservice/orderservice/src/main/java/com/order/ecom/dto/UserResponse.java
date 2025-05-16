package com.order.ecom.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private List<AddressDTO> address;
}
