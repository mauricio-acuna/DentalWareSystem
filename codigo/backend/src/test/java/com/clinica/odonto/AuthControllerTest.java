package com.clinica.odonto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void creaUsuarioYPermiteLoginConJwt() throws Exception {
        String usuario = """
                {
                  "nombreCompleto":"Dra. Laura Clinica",
                  "email":"laura.seguridad@example.com",
                  "passwordTemporal":"Temporal1234",
                  "roles":["ADMIN","ODONTOLOGO"]
                }
                """;

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuario))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("laura.seguridad@example.com"))
                .andExpect(jsonPath("$.roles", hasItem("ADMIN")))
                .andExpect(jsonPath("$.requiereCambioPassword").value(true));

        String login = """
                {
                  "email":"laura.seguridad@example.com",
                  "password":"Temporal1234"
                }
                """;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(login))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.accessToken", not(blankOrNullString())))
                .andExpect(jsonPath("$.roles", hasItem("ADMIN")));
    }

    @Test
    void rechazaLoginConPasswordIncorrecto() throws Exception {
        String usuario = """
                {
                  "nombreCompleto":"Recepcion Seguridad",
                  "email":"recepcion.seguridad@example.com",
                  "passwordTemporal":"Temporal1234",
                  "roles":["RECEPCION"]
                }
                """;

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuario))
                .andExpect(status().isCreated());

        String login = """
                {
                  "email":"recepcion.seguridad@example.com",
                  "password":"incorrecta"
                }
                """;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(login))
                .andExpect(status().isUnauthorized());
    }
}
