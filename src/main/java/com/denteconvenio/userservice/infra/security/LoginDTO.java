package com.denteconvenio.userservice.infra.security;

import jakarta.validation.constraints.Email;

public record LoginDTO(

    @Email
    String login,
    
    String senha


) {



}
