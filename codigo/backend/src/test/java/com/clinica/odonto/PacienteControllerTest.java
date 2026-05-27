package com.clinica.odonto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PacienteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void creaPacienteYLoLista() throws Exception {
        mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJson("12345678X", "ana@example.com", true)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPaciente").exists())
                .andExpect(jsonPath("$.estado").value("ACTIVO"))
                .andExpect(jsonPath("$.edad").isNumber());

        mockMvc.perform(get("/pacientes?estado=ACTIVO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].email", hasItem("ana@example.com")));
    }

    @Test
    void rechazaPacienteSinConsentimientoRgpd() throws Exception {
        mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJson("87654321Z", "rgpd@example.com", false)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("El consentimiento RGPD es obligatorio para crear pacientes"));
    }

    @Test
    void rechazaPacienteDuplicado() throws Exception {
        String body = pacienteJson("99999999A", "dup@example.com", true);
        mockMvc.perform(post("/pacientes").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/pacientes").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isConflict());
    }

    static String pacienteJson(String identificacion, String email, boolean consentimiento) {
        return """
                {
                  "numeroIdentificacion": "%s",
                  "nombreCompleto": "Ana",
                  "apellidos": "Garcia Lopez",
                  "fechaNacimiento": "1990-05-15",
                  "genero": "F",
                  "email": "%s",
                  "telefonoMovil": "+34612345678",
                  "direccion": {
                    "calle": "Calle Principal",
                    "numero": "123",
                    "codigoPostal": "28001",
                    "ciudad": "Madrid",
                    "provincia": "Madrid",
                    "pais": "Espana"
                  },
                  "consentimientoRGPD": %s,
                  "historialAlergias": ["Penicilina"],
                  "patologiasBase": ["Diabetes"],
                  "medicamentosActuales": ["Metformina"]
                }
                """.formatted(identificacion, email, consentimiento);
    }
}
