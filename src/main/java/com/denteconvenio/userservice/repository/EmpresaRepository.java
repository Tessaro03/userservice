package com.denteconvenio.userservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.denteconvenio.userservice.domain.organizacao.empresa.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, UUID>{
    
}
