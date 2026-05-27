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

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CitaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void programaYConfirmaCita() throws Exception {
        String patientPayload = mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PacienteControllerTest.pacienteJson("11112222X", "cita@example.com", true)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        JsonNode patient = objectMapper.readTree(patientPayload);
        String tomorrow = LocalDate.now().plusDays(1).toString();
        String citaPayload = """
                {
                  "idPaciente": "%s",
                  "idDentista": "%s",
                  "fechaCita": "%s",
                  "horaInicio": "10:00",
                  "horaFin": "10:30",
                  "tipoCita": "CONSULTA",
                  "motivoConsulta": "Revision general",
                  "prioridad": "MEDIA"
                }
                """.formatted(patient.get("idPaciente").asText(), UUID.randomUUID(), tomorrow);

        String response = mockMvc.perform(post("/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(citaPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("PROGRAMADA"))
                .andReturn().getResponse().getContentAsString();

        String idCita = objectMapper.readTree(response).get("idCita").asText();
        mockMvc.perform(post("/citas/{id}/confirmar", idCita))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("CONFIRMADA"));
    }

    @Test
    void rechazaCitaConHorarioInvalido() throws Exception {
        String patientPayload = mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PacienteControllerTest.pacienteJson("22223333Y", "horario@example.com", true)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        String idPaciente = objectMapper.readTree(patientPayload).get("idPaciente").asText();

        String payload = """
                {
                  "idPaciente": "%s",
                  "fechaCita": "%s",
                  "horaInicio": "10:30",
                  "horaFin": "10:00",
                  "tipoCita": "CONSULTA"
                }
                """.formatted(idPaciente, LocalDate.now().plusDays(1));

        mockMvc.perform(post("/citas").contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("La hora de fin debe ser posterior a la hora de inicio"));
    }
}
