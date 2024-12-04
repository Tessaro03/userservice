package com.denteconvenio.userservice.domain.organizacao.consultorio.endereco;

import java.io.Serializable;

public record EnderecoResponseDTO(

    String cep,
    String estado,
    String cidade,
    String rua,
    String numero

) implements Serializable{

    public EnderecoResponseDTO(Endereco endereco){
        this(endereco.getCep(), endereco.getEstado(), endereco.getCidade(), endereco.getRua(), endereco.getNumero());
    }
}
