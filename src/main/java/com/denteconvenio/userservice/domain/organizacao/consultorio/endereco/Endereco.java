package com.denteconvenio.userservice.domain.organizacao.consultorio.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private String cep;
    private String estado;
    private String cidade;
    private String rua;
    private String numero;


}