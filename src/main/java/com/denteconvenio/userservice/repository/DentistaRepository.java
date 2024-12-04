package com.denteconvenio.userservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.denteconvenio.userservice.domain.pessoal.dentista.Dentista;

import io.lettuce.core.dynamic.annotation.Param;


public interface DentistaRepository extends JpaRepository<Dentista, UUID>{

    @Modifying
    @Query(value="DELETE FROM consultorios_dentistas WHERE dentistas_id = :idDentista", nativeQuery=true)
    int removerConsultorios(@Param("idDentista") UUID idDentista);
}
