package com.clinica.odonto.model;

import com.clinica.odonto.model.Enums.EstadoUsuario;
import com.clinica.odonto.model.Enums.RolUsuario;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "usuarios_sistema", uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuarios_sistema_email", columnNames = "email")
})
public class UsuarioSistema {
    @Id
    @GeneratedValue
    private UUID idUsuario;

    @Column(nullable = false, length = 120)
    private String nombreCompleto;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false, length = 120)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoUsuario estado = EstadoUsuario.ACTIVO;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "id_usuario"))
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, length = 30)
    private Set<RolUsuario> roles = new LinkedHashSet<>();

    @Column(nullable = false)
    private boolean requiereCambioPassword = true;

    private Instant ultimoAcceso;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant creadoEn;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant actualizadoEn;

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    public Set<RolUsuario> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolUsuario> roles) {
        this.roles = roles;
    }

    public boolean isRequiereCambioPassword() {
        return requiereCambioPassword;
    }

    public void setRequiereCambioPassword(boolean requiereCambioPassword) {
        this.requiereCambioPassword = requiereCambioPassword;
    }

    public Instant getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(Instant ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }
}
