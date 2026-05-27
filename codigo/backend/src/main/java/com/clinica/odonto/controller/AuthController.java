package com.clinica.odonto.controller;

import com.clinica.odonto.dto.AuthResponse;
import com.clinica.odonto.dto.LoginRequest;
import com.clinica.odonto.dto.UsuarioSistemaRequest;
import com.clinica.odonto.dto.UsuarioSistemaResponse;
import com.clinica.odonto.service.UsuarioSistemaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {
    private final UsuarioSistemaService service;

    public AuthController(UsuarioSistemaService service) {
        this.service = service;
    }

    @PostMapping("/auth/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return service.login(request);
    }

    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioSistemaResponse crear(@Valid @RequestBody UsuarioSistemaRequest request) {
        return service.crear(request);
    }
}
