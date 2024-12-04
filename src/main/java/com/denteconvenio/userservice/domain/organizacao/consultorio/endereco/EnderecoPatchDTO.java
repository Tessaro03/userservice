package com.denteconvenio.userservice.domain.organizacao.consultorio.endereco;

public record EnderecoPatchDTO(

    String cep,
    String estado,
    String cidade,
    String rua,
    String numero

) {

}
