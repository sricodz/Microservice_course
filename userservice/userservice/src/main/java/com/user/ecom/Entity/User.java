package com.user.ecom.Entity;

import com.user.ecom.custom.PhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Indexed;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "FirstName cannot be Null")
    private String firstName;

    @NotNull(message = "LastName cannot be Null")
    private String lastName;

    @Email
    @NotNull(message = "Email cannot be Null")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Phone cannot be Null")
    @PhoneNumber(message = "Phone Number must be 10 digits")
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.CUSTOMER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> address;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
