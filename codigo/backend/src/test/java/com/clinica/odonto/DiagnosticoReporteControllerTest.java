package com.clinica.odonto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DiagnosticoReporteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void creaDiagnosticoYConsultaReportes() throws Exception {
        String patientPayload = mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PacienteControllerTest.pacienteJson("55556666Y", "diagnostico@example.com", true)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        String idPaciente = objectMapper.readTree(patientPayload).get("idPaciente").asText();

        String diagnosticoPayload = """
                {
                  "codigoCIE10": "K02",
                  "descripcionDiagnostico": "Caries dental",
                  "dienteAfectado": "14",
                  "fechaDiagnostico": "2026-05-28",
                  "severidad": "MODERADA",
                  "confirmadoLaboratorio": false,
                  "planTratamientoRecomendado": "Obturacion",
                  "urgenciaTratamiento": false
                }
                """;

        mockMvc.perform(post("/pacientes/{idPaciente}/diagnosticos", idPaciente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(diagnosticoPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigoCIE10").value("K02"))
                .andExpect(jsonPath("$.descripcionDiagnostico").value("Caries dental"));

        mockMvc.perform(get("/pacientes/{idPaciente}/diagnosticos", idPaciente))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(1)));

        mockMvc.perform(get("/reportes/actividad")
                        .param("fechaInicio", LocalDate.now().minusDays(1).toString())
                        .param("fechaFin", LocalDate.now().plusDays(7).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.citasTotales").isNumber());

        mockMvc.perform(get("/reportes/ingresos")
                        .param("fechaInicio", LocalDate.now().minusDays(1).toString())
                        .param("fechaFin", LocalDate.now().plusDays(1).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.facturado").isNumber());
    }

    @Test
    void disponibilidadMarcaSlotOcupado() throws Exception {
        String patientPayload = mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PacienteControllerTest.pacienteJson("66667777Z", "disponibilidad@example.com", true)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        String idPaciente = objectMapper.readTree(patientPayload).get("idPaciente").asText();
        String idDentista = "11111111-1111-1111-1111-111111111111";
        String fecha = LocalDate.now().plusDays(2).toString();

        String citaPayload = """
                {
                  "idPaciente": "%s",
                  "idDentista": "%s",
                  "fechaCita": "%s",
                  "horaInicio": "10:00",
                  "horaFin": "10:30",
                  "tipoCita": "CONSULTA"
                }
                """.formatted(idPaciente, idDentista, fecha);

        mockMvc.perform(post("/citas").contentType(MediaType.APPLICATION_JSON).content(citaPayload))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/disponibilidad")
                        .param("fechaInicio", fecha)
                        .param("fechaFin", fecha)
                        .param("idDentista", idDentista))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.disponibilidad[0].horas[2].hora").value("10:00:00"))
                .andExpect(jsonPath("$.disponibilidad[0].horas[2].disponible").value(false));
    }
}
