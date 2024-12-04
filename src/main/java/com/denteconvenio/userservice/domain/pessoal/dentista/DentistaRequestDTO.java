package com.denteconvenio.userservice.domain.pessoal.dentista;

import jakarta.validation.constraints.NotBlank;

public record DentistaRequestDTO(

    @NotBlank
    String nome,
    @NotBlank
    String email,
    @NotBlank
    String senha,
    @NotBlank
    String cpf,
    String telefone,
    @NotBlank
    String cro 

) {
    
}
