package com.denteconvenio.userservice.domain.pessoal.beneficiario;

import java.io.Serializable;
import java.util.UUID;

public record BeneficiarioResponseDTO( 

    UUID id,
    String nome,
    String email,
    String cpf,
    String telefone,
    Boolean ativo
    
) implements Serializable {

    public BeneficiarioResponseDTO(Beneficiario beneficiario){
        this(beneficiario.getId(), beneficiario.getNome(), beneficiario.getEmail(), beneficiario.getCpf(), 
        beneficiario.getTelefone(), beneficiario.getAtivo());
    }

}
