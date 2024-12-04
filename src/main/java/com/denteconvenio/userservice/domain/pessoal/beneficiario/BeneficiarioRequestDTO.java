package com.denteconvenio.userservice.domain.pessoal.beneficiario;

import jakarta.validation.constraints.NotBlank;

public record BeneficiarioRequestDTO(

    @NotBlank
    String nome,
    
    @NotBlank
    String email,

    @NotBlank
    String senha,
 
    @NotBlank
    String cpf,
 
    String telefone

) {
    
}
