package com.denteconvenio.userservice.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.denteconvenio.userservice.domain.pessoal.beneficiario.Beneficiario;

public interface BeneficiarioRepository extends JpaRepository<Beneficiario, UUID>{



    Page<Beneficiario> findAllByEmpresaId(UUID idEmpresa, Pageable pageable);
    
}
