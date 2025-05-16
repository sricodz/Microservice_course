package com.user.ecom.repository;

import com.user.ecom.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
    boolean existsByEmail(String email);
}
