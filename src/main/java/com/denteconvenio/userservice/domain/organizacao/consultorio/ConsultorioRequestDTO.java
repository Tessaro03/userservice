package com.denteconvenio.userservice.domain.organizacao.consultorio;

import com.denteconvenio.userservice.domain.organizacao.consultorio.endereco.EnderecoRequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ConsultorioRequestDTO(

    @NotBlank String nome,
    @Email String email,
    @NotBlank String senha,
    @NotBlank String cnpj,
    @NotBlank String telefone,
    @Valid EnderecoRequestDTO endereco
) 
{
}
