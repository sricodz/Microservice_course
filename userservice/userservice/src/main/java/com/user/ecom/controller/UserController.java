package com.user.ecom.controller;

import com.user.ecom.dto.UserRequest;
import com.user.ecom.dto.UserResponse;
import com.user.ecom.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/v1")
@Slf4j
public class UserController {

    @Autowired
    private UserService userServ;

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userServ.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String email){
        return userServ.fetchUser(email)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequest userReq) {
        userServ.addUser(userReq);
        return ResponseEntity.ok("User Added SuccessFully");
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> updateUser(@PathVariable String email,
                                             @Valid @RequestBody UserRequest userReq){
        userServ.updateUser(email,userReq);
        return ResponseEntity.ok("User Updated Successfully");
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteByEmail(@PathVariable String email){
        userServ.deleteUserByEmail(email);
        return ResponseEntity.ok("User Deleteld Successfully");
    }
}
