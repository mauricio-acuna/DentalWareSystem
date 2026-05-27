package com.clinica.odonto.service;

import com.clinica.odonto.dto.AuthResponse;
import com.clinica.odonto.dto.LoginRequest;
import com.clinica.odonto.dto.UsuarioSistemaRequest;
import com.clinica.odonto.dto.UsuarioSistemaResponse;
import com.clinica.odonto.exception.BusinessRuleException;
import com.clinica.odonto.model.Enums.EstadoUsuario;
import com.clinica.odonto.model.UsuarioSistema;
import com.clinica.odonto.repository.UsuarioSistemaRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class UsuarioSistemaService implements UserDetailsService {
    private final UsuarioSistemaRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuditoriaService auditoriaService;

    public UsuarioSistemaService(UsuarioSistemaRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuditoriaService auditoriaService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.auditoriaService = auditoriaService;
    }

    @Transactional
    public UsuarioSistemaResponse crear(UsuarioSistemaRequest request) {
        String email = normalizarEmail(request.email());
        if (repository.existsByEmailIgnoreCase(email)) {
            throw new BusinessRuleException("Ya existe un usuario con ese email");
        }

        UsuarioSistema usuario = new UsuarioSistema();
        usuario.setNombreCompleto(request.nombreCompleto());
        usuario.setEmail(email);
        usuario.setPasswordHash(passwordEncoder.encode(request.passwordTemporal()));
        usuario.setRoles(request.roles());
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.setRequiereCambioPassword(true);
        UsuarioSistema guardado = repository.save(usuario);
        auditoriaService.registrarCreacion("UsuarioSistema", guardado.getIdUsuario(), "Usuario creado: " + email);
        return toResponse(guardado);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        String email = normalizarEmail(request.email());
        UsuarioSistema usuario = repository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new BadCredentialsException("Credenciales invalidas"));
        if (usuario.getEstado() != EstadoUsuario.ACTIVO || !passwordEncoder.matches(request.password(), usuario.getPasswordHash())) {
            auditoriaService.registrarAccesoDenegado("UsuarioSistema", usuario.getIdUsuario(), "Login denegado: " + email);
            throw new BadCredentialsException("Credenciales invalidas");
        }
        usuario.setUltimoAcceso(Instant.now());
        String token = jwtService.emitir(usuario);
        auditoriaService.registrarAcceso("UsuarioSistema", usuario.getIdUsuario(), "Login correcto: " + email);
        return new AuthResponse(
                token,
                "Bearer",
                jwtService.calcularExpiracion(),
                usuario.getIdUsuario(),
                usuario.getNombreCompleto(),
                usuario.getEmail(),
                usuario.getRoles(),
                usuario.isRequiereCambioPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioSistema usuario = repository.findByEmailIgnoreCase(normalizarEmail(username))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        List<SimpleGrantedAuthority> authorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.name()))
                .toList();
        return User.withUsername(usuario.getEmail())
                .password(usuario.getPasswordHash())
                .authorities(authorities)
                .disabled(usuario.getEstado() != EstadoUsuario.ACTIVO)
                .build();
    }

    private UsuarioSistemaResponse toResponse(UsuarioSistema usuario) {
        return new UsuarioSistemaResponse(
                usuario.getIdUsuario(),
                usuario.getNombreCompleto(),
                usuario.getEmail(),
                usuario.getEstado(),
                usuario.getRoles(),
                usuario.isRequiereCambioPassword());
    }

    private String normalizarEmail(String email) {
        return email.trim().toLowerCase();
    }
}
