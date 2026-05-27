package com.clinica.odonto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FacturaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void creaFacturaCalculaTotalesYRegistraPago() throws Exception {
        String patientPayload = mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PacienteControllerTest.pacienteJson("33334444Z", "factura@example.com", true)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        String idPaciente = objectMapper.readTree(patientPayload).get("idPaciente").asText();

        String facturaPayload = """
                {
                  "idPaciente": "%s",
                  "tipoFactura": "ORDINARIA",
                  "diasCredito": 15,
                  "lineas": [
                    {
                      "concepto": "Consulta general",
                      "cantidad": 1,
                      "precioUnitario": 100.00,
                      "ivaPercent": 21
                    }
                  ]
                }
                """.formatted(idPaciente);

        String facturaResponse = mockMvc.perform(post("/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(facturaPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.baseImponible").value(100.00))
                .andExpect(jsonPath("$.iva").value(21.00))
                .andExpect(jsonPath("$.importeTotal").value(121.00))
                .andReturn().getResponse().getContentAsString();

        String idFactura = objectMapper.readTree(facturaResponse).get("idFactura").asText();
        String pagoPayload = """
                {
                  "formaPago": "TARJETA",
                  "importePago": 121.00,
                  "numeroTransaccion": "TR-001"
                }
                """;

        mockMvc.perform(post("/facturas/{id}/pagos", idFactura)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pagoPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estadoFactura").value("PAGADA"));
    }
}
