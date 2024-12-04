package com.denteconvenio.userservice.domain.organizacao.consultorio;

import java.util.List;
import java.util.UUID;

import com.denteconvenio.userservice.domain.organizacao.consultorio.endereco.Endereco;
import com.denteconvenio.userservice.domain.pessoal.dentista.Dentista;
import com.denteconvenio.userservice.domain.user.Tipo;
import com.denteconvenio.userservice.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="consultorios")
@Getter
@Setter
@NoArgsConstructor
public class Consultorio extends User {

    @Column(unique=true)
    private String cnpj;

    @ManyToMany
    private List<Dentista> dentistas;
    
    @Embedded
    private Endereco endereco;

    private UUID idPlano;

    public Consultorio(ConsultorioBuilder builder) {
        super(builder.nome, builder.email, builder.senha, builder.telefone, builder.ativo, Tipo.Consultorio);
        this.cnpj = builder.cnpj;
        this.endereco = builder.endereco;
    }

    public static class ConsultorioBuilder{

        private String nome;
        private String email;
        private String senha;
        private String cnpj;
        private String telefone;
        private Boolean ativo;
        private Endereco endereco;

        public ConsultorioBuilder(String nome, String email, String senha, String cnpj) {
            this.nome = nome;
            this.email = email;
            this.senha = senha;
            this.cnpj = cnpj;
        }

        public ConsultorioBuilder ativo(Boolean ativo){
            this.ativo = ativo;
            return this;
        }

        public ConsultorioBuilder telefone(String telefone){
            this.telefone = telefone;
            return this;
        }
        
        public ConsultorioBuilder endereco(String cep,String estado,String cidade,String rua, String numero){
            this.endereco = new Endereco(cep, estado, cidade, rua, numero);
            return this;
        }


        public Consultorio build(){
            return new Consultorio(this);
        }
    }


}
