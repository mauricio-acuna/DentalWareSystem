package com.clinica.odonto.repository;

import com.clinica.odonto.model.UsuarioSistema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistema, UUID> {
    Optional<UsuarioSistema> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}
