package com.denteconvenio.userservice.infra.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(

    @Email
    String login,
    
    @NotBlank
    String senha


) {



}
