package com.denteconvenio.userservice.domain.organizacao.consultorio;

import com.denteconvenio.userservice.domain.organizacao.consultorio.endereco.EnderecoRequestDTO;

public record ConsultorioRequestDTO(

    String nome,
    String email,
    String senha,
    String cnpj,
    String telefone,
    EnderecoRequestDTO endereco
) 
{
}
