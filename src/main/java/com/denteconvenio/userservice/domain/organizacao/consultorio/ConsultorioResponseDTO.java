package com.denteconvenio.userservice.domain.organizacao.consultorio;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.denteconvenio.userservice.domain.organizacao.consultorio.endereco.EnderecoResponseDTO;
import com.denteconvenio.userservice.domain.pessoal.dentista.DentistaResponseDTO;

public record ConsultorioResponseDTO(

        UUID id,
        String nome,
        String email,
        String cnpj,
        String telefone,
        Boolean ativo,
        EnderecoResponseDTO endereco,
        List<DentistaResponseDTO> dentistas
        
) implements Serializable{
    public ConsultorioResponseDTO(Consultorio consultorio){
        this(consultorio.getId(), consultorio.getNome(), consultorio.getEmail(), consultorio.getCnpj(), consultorio.getTelefone(), consultorio.getAtivo()
        , new EnderecoResponseDTO(consultorio.getEndereco()) , consultorio.getDentistas().stream().map(DentistaResponseDTO::new).collect(Collectors.toList()));
    }
}
