package com.example.store.Backend.employees;

import com.example.store.Backend.enumerations.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Employee {
    private @Id
    @GeneratedValue Long id;
    private String createdBy;
    private String updatedBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @NotNull
    private String firstName;
    private String lastName;
    @Email
    @NotNull
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
   @ElementCollection(fetch = FetchType.EAGER)
   private List<GrantedAuthority> authorities= new ArrayList<>();
    private boolean isLocked;

    public UserDetails asUser() {
        return new User(email, password, authorities);
    }
}
