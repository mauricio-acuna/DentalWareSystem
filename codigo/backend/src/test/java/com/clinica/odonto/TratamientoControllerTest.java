package com.clinica.odonto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TratamientoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void creaTratamientoRegistraSesionCompletaYAudita() throws Exception {
        String patientPayload = mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PacienteControllerTest.pacienteJson("44445555X", "tratamiento@example.com", true)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        String idPaciente = objectMapper.readTree(patientPayload).get("idPaciente").asText();

        String tratamientoPayload = """
                {
                  "nombreTratamiento": "Obturacion composite",
                  "descripcionDetallada": "Restauracion de pieza",
                  "clasificacion": "RESTAURADORA",
                  "dienteAfectado": "14",
                  "planTratamiento": "Preparacion y obturacion",
                  "presupuesto": 85.50,
                  "duracionEstimadaMinutos": 45
                }
                """;

        String tratamientoResponse = mockMvc.perform(post("/pacientes/{idPaciente}/tratamientos", idPaciente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tratamientoPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("PENDIENTE"))
                .andExpect(jsonPath("$.codigoTratamiento").exists())
                .andReturn().getResponse().getContentAsString();
        JsonNode tratamiento = objectMapper.readTree(tratamientoResponse);
        String idTratamiento = tratamiento.get("idTratamiento").asText();

        String sesionPayload = """
                {
                  "numeroSesion": 1,
                  "fechaRealizacion": "2026-05-28",
                  "tiempoReal": 40,
                  "procedimientos": ["Anestesia local", "Preparacion cavitaria"],
                  "complicaciones": "",
                  "proximaSesion": "2026-06-04"
                }
                """;

        mockMvc.perform(post("/tratamientos/{idTratamiento}/sesiones", idTratamiento)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sesionPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroSesion").value(1));

        String completarPayload = """
                {
                  "resultadoClinico": "Excelente",
                  "observaciones": "Paciente tolero bien el tratamiento",
                  "proximoControl": "2026-08-28"
                }
                """;

        mockMvc.perform(put("/tratamientos/{idTratamiento}/completar", idTratamiento)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(completarPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("COMPLETADO"))
                .andExpect(jsonPath("$.sesiones.length()").value(1));

        mockMvc.perform(get("/auditoria/eventos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(3)));
    }
}
