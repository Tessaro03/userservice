package com.denteconvenio.userservice.domain.organizacao.empresa;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.denteconvenio.userservice.domain.pessoal.beneficiario.BeneficiarioResponseDTO;

public record EmpresaResponseDTO(

        UUID id,
        String nome,
        String email,
        String cnpj,
        String telefone,
        Boolean ativo,
        List<BeneficiarioResponseDTO> colaboradores

) implements Serializable{

    public EmpresaResponseDTO(Empresa empresa){
        this(empresa.getId(), empresa.getNome(), empresa.getEmail(), empresa.getCnpj(), empresa.getTelefone(), empresa.getAtivo(),
        empresa.getColaboradores().stream().map(BeneficiarioResponseDTO::new).collect(Collectors.toList()));
    }
    
}
