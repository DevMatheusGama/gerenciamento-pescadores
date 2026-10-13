package dev.matheusGama.gerenciamento_pescadores_api.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveBloquearCadastroSemAutenticacao() throws Exception {
        mockMvc.perform(post("/pescadores"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deveBloquearCadastroParaUsuarioComPerfilUser()
            throws Exception {

        mockMvc.perform(post("/pescadores"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deveBloquearAtualizacaoParaUsuarioComPerfilUser()
            throws Exception {

        mockMvc.perform(put("/pescadores/00000000-0000-0000-0000-000000000001"))
                .andExpect(status().isForbidden());
    }
}