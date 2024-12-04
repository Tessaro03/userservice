package com.denteconvenio.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.denteconvenio.userservice.infra.security.LoginDTO;
import com.denteconvenio.userservice.service.user.UserService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService service;


    @PostMapping("/login")
    public ResponseEntity efeturaLogin(@RequestBody @Valid LoginDTO dados){
        return ResponseEntity.ok(service.efetuarLogin(dados)); 
    }

    @GetMapping
    public ResponseEntity getMethodName() {
        return ResponseEntity.ok("Passou");
    }
    
    

}
