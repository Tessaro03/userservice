package com.denteconvenio.userservice.domain.organizacao.empresa;

import java.util.List;

import com.denteconvenio.userservice.domain.pessoal.beneficiario.Beneficiario;
import com.denteconvenio.userservice.domain.user.Tipo;
import com.denteconvenio.userservice.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="empresas")
@Setter
@Getter
@NoArgsConstructor
public class Empresa extends User {

    @Column(unique=true)
    private String cnpj;


    @OneToMany(mappedBy="empresa")
    private List<Beneficiario> colaboradores;

    private Empresa(EmpresaBuilder builder){
        super(builder.nome,builder.email, builder.senha , builder.telefone, builder.ativo, Tipo.Empresa);
        this.cnpj = builder.cnpj;
    }

    
    public static class EmpresaBuilder{

        private String nome;
        private String email;
        private String senha;
        private String cnpj;
        private String telefone;
        private Boolean ativo;
        private List<Beneficiario> colaboradores;

        public EmpresaBuilder(String nome, String email, String senha, String cnpj) {
            this.nome = nome;
            this.email = email;
            this.senha = senha;
            this.cnpj = cnpj;
        }


        public EmpresaBuilder telefone(String telefone){
            this.telefone = telefone;
            return this;
        }
        
        public EmpresaBuilder ativo(Boolean ativo){
            this.ativo = ativo;
            return this;
        }


        public Empresa build(){
            return new Empresa(this);
        }

    }



    
}
