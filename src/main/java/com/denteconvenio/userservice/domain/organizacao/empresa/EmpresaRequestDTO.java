package com.denteconvenio.userservice.domain.organizacao.empresa;

import jakarta.validation.constraints.NotBlank;

public record EmpresaRequestDTO(

    @NotBlank
    String nome,
    @NotBlank
    String email,
    
    @NotBlank
    String senha,
    
    @NotBlank
    String cnpj,
    String telefone

) {
    
}
