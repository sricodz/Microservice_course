package com.user.ecom.service;

import com.user.ecom.Entity.Address;
import com.user.ecom.Entity.User;
import com.user.ecom.Entity.UserRole;
import com.user.ecom.dto.UserRequest;
import com.user.ecom.dto.UserResponse;
import com.user.ecom.exception.UserAlreadyExistsException;
import com.user.ecom.exception.UserNotFoundException;
import com.user.ecom.mapper.UserMapper;
import com.user.ecom.repository.AddressRepository;
import com.user.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AddressRepository addRepo;

    @Autowired
    private UserMapper userMapper;

    public List<UserResponse> fetchAllUsers(){
        return userRepo.findAll()
                .stream()
                .map(userMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addUser(UserRequest userReq) {

        if(userRepo.existsByEmail(userReq.getEmail())) {
            throw  new UserAlreadyExistsException(userReq.getEmail());
        }

        User u = userMapper.mapToUser(userReq);
        u.setCreatedAt(LocalDateTime.now());
        userRepo.save(u);
    }

    public Optional<UserResponse> fetchUser(String email) {
        return userRepo.findByEmail(email)
                .map(userMapper::mapToUserResponse);
    }

    @Transactional
    public void deleteUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException(email));
        userRepo.deleteByEmail(email);
    }

    @Transactional
    public void updateUser(String email,UserRequest uReq){

        User existingUser = userRepo.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException(email));

        existingUser.setFirstName(uReq.getFirstName());
        existingUser.setLastName(uReq.getLastName());
        existingUser.setPhone(uReq.getPhone());
        existingUser.setEmail(uReq.getEmail());
        existingUser.setRole(UserRole.valueOf(uReq.getRole()));

        existingUser.getAddress().clear();

        List<Address> updateAddress = uReq.getAddress()
                .stream()
                .map(aDto->{
                    Address a = new Address();
                    a.setStreet(aDto.getStreet());
                    a.setCity(aDto.getCity());
                    a.setZipcode(aDto.getZipcode());
                    a.setCountry(aDto.getCountry());
                    a.setState(aDto.getState());
                    a.setUser(existingUser);
                    return a;
                }).collect(Collectors.toList());
        existingUser.getAddress().addAll(updateAddress);
        existingUser.setUpdatedAt(LocalDateTime.now());
        userRepo.save(existingUser);
    }
}
