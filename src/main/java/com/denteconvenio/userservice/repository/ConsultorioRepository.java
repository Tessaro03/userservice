package com.denteconvenio.userservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.denteconvenio.userservice.domain.organizacao.consultorio.Consultorio;

public interface ConsultorioRepository extends JpaRepository<Consultorio, UUID>{
    
}
