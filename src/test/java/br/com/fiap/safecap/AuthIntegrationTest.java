
package br.com.fiap.safecap;

import br.com.fiap.safecap.dto.UsuarioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAuthAndAccessProtectedRoute() throws Exception {
        WebTestClient client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        UsuarioDTO novoUsuario = new UsuarioDTO();
        novoUsuario.setNome("Teste");
        novoUsuario.setEmail("teste@fiap.com");
        novoUsuario.setSenha("123456");

        client.post().uri("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(novoUsuario))
                .exchange()
                .expectStatus().isOk();

        var tokenResponse = client.post().uri("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(novoUsuario))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.token").exists()
                .returnResult();

        String token = new String(tokenResponse.getResponseBody());
        token = token.replace("{"token":"", "").replace(""}", "");

        client.get().uri("/api/alertas")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testAccessProtectedRouteWithoutTokenShouldFail() {
        WebTestClient client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        client.get().uri("/api/alertas")
                .exchange()
                .expectStatus().isUnauthorized();
    }
}
