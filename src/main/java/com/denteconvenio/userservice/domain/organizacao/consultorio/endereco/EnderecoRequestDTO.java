package com.denteconvenio.userservice.domain.organizacao.consultorio.endereco;

import jakarta.validation.constraints.NotBlank;

public record EnderecoRequestDTO(

    @NotBlank
    String cep,
    @NotBlank
    String estado,
    @NotBlank
    String cidade,
    @NotBlank
    String rua,
    @NotBlank
    String numero

) {
}
