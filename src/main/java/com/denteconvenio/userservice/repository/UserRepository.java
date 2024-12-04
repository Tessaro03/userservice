package com.denteconvenio.userservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.denteconvenio.userservice.domain.user.User;

public interface UserRepository extends  JpaRepository<User, UUID>{
    
    UserDetails findByEmail(String email);


}
