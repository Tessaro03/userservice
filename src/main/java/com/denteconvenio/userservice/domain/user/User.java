package com.denteconvenio.userservice.domain.user;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails{

    public User(String nome, String email, String senha,  String telefone, boolean ativo, Tipo tipo) {
        this.nome = nome;
        this.email = email;
        this.senha = new BCryptPasswordEncoder().encode(senha);
        this.telefone = telefone;
        this.ativo = ativo;
        this.tipo = tipo;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private String nome;
    
    @Column(unique=true)
    private String email;
    private String senha;
    private String telefone;
    private Boolean ativo;

    private Tipo tipo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        return switch (this.tipo) {
            case Beneficiario -> List.of(new SimpleGrantedAuthority("ROLE_BENEFICIARIO"));
            case Empresa -> List.of(new SimpleGrantedAuthority("ROLE_EMPRESA"));
            case Dentista -> List.of(new SimpleGrantedAuthority("ROLE_DENTISTA"));
            case Consultorio -> List.of(new SimpleGrantedAuthority("ROLE_CONSULTORIO"));
            default -> List.of();
        }; // Sem permissões
    }

    @Override
    public String getPassword() {
        return senha;    
    }

    @Override
    public String getUsername() {
        return email;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    
}
