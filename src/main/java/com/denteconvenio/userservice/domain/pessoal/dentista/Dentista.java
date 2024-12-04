package com.denteconvenio.userservice.domain.pessoal.dentista;

import java.util.List;

import com.denteconvenio.userservice.domain.organizacao.consultorio.Consultorio;
import com.denteconvenio.userservice.domain.user.Tipo;
import com.denteconvenio.userservice.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dentistas")
@Getter
@Setter
@NoArgsConstructor
public class Dentista extends User{
    
    @Column(unique=true)
    private String cro;

    @Column(unique = true)
    private String cpf;

    @ManyToMany
    private List<Consultorio> consultorios;

    private Dentista(DentistaBuilder builder) { 
        super(builder.nome, builder.email, builder.senha, builder.telefone, builder.ativo, Tipo.Dentista);
        this.cro = builder.cro;
        this.cpf = builder.cpf;
    }

    public static class DentistaBuilder{

        private String nome;
        private String email;
        private String senha;
        private String cpf;
        private String telefone;
        private Boolean ativo;
        private String cro;

        public DentistaBuilder(String nome, String email, String senha, String cpf) {
            this.nome = nome;
            this.email = email;
            this.senha = senha;
            this.cpf = cpf;
        }

        public DentistaBuilder cro(String cro){
            this.cro = cro;
            return this;
        }

        public DentistaBuilder telefone(String telefone){
            this.telefone = telefone;
            return this;
        }

        public DentistaBuilder ativo(Boolean ativo){
            this.ativo = ativo;
            return this;
        }

        public Dentista build(){
            return new Dentista(this);
        }

    }

}
