package com.denteconvenio.userservice.domain.pessoal.dentista;

import java.io.Serializable;
import java.util.UUID;

public record DentistaResponseDTO(

    UUID id,
    String nome,
    String email,
    String cpf,
    String telefone,
    Boolean ativo,
    String cro 

) implements Serializable{

    public DentistaResponseDTO(Dentista dentista){
        this(dentista.getId(), dentista.getNome(), dentista.getEmail(),dentista.getCpf(),dentista.getTelefone(), dentista.getAtivo(),dentista.getCro());
    }

}
