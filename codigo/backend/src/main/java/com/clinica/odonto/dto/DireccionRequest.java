package com.clinica.odonto.dto;

public record DireccionRequest(
        String calle,
        String numero,
        String codigoPostal,
        String ciudad,
        String provincia,
        String pais
) {
}
