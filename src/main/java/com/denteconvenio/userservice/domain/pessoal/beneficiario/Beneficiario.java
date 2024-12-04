package com.denteconvenio.userservice.domain.pessoal.beneficiario;

import com.denteconvenio.userservice.domain.organizacao.empresa.Empresa;
import com.denteconvenio.userservice.domain.user.Tipo;
import com.denteconvenio.userservice.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "beneficiarios")
@Getter
@Setter
@NoArgsConstructor
public class Beneficiario extends User{
    
    public Beneficiario(BeneficiarioBuilder builder) {
        super(builder.nome, builder.email, builder.senha, builder.telefone, builder.ativo, Tipo.Beneficiario);
        this.cpf = builder.cpf;
    }
    
    @Column(unique = true)
    private String cpf;
    
    @ManyToOne
    @JoinColumn(name="empresa_id")
    private Empresa empresa;
    
    public static class BeneficiarioBuilder{

        private String nome;
        private String email;
        private String senha;
        private String cpf;
        private String telefone;
        private Boolean ativo;
        private Empresa empresa;


        public BeneficiarioBuilder(String nome, String email, String senha, String cpf) {
            this.nome = nome;
            this.email = email;
            this.senha = senha;
            this.cpf = cpf;
        }
        
        public BeneficiarioBuilder telefone(String telefone){
            this.telefone = telefone;
            return this;
        }
    
        public BeneficiarioBuilder ativo(Boolean ativo){
            this.ativo = ativo;
            return this;
        }


        public BeneficiarioBuilder empresa(Empresa empresa){
            this.empresa = empresa;
            return this;
        }
        
        public Beneficiario build(){
            return new Beneficiario(this);
        }
    
    }

}
 