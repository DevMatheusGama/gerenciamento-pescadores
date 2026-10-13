package dev.matheusGama.gerenciamento_pescadores_api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.matheusGama.gerenciamento_pescadores_api.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtServiceTest {

    private JwtService jwtService;
    private Usuario usuario;

    private final String secret = "teste@teste@teste@teste";

    @BeforeEach
    void setup() {
        jwtService = new JwtService();

        ReflectionTestUtils.setField(jwtService, "secret", secret);

        usuario = mock(Usuario.class);
        when(usuario.getEmail()).thenReturn("usuario@gmail.com");
    }

    @Test
    void deveGerarTokenComSucesso() {
        String token = jwtService.generateToken(usuario);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void deveRetornarEmailAoValidarToken() {
        String token = jwtService.generateToken(usuario);

        String email = jwtService.validateToken(token);

        assertNotNull(email);
        assertEquals(
                "usuario@gmail.com",
                email
        );
    }

    @Test
    void deveGerarTokenComIssuerCorreto() {
        String token = jwtService.generateToken(usuario);

        String issuer = JWT.decode(token).getIssuer();

        assertNotNull(issuer);
        assertEquals(
                "api_pescadores",
                issuer
        );
    }

    @Test
    void deveRejeitarTokenComIssuerIncorreto() {
        String token = JWT.create()
                .withSubject("admin@teste.com")
                .withIssuer("outro_emissor")
                .sign(Algorithm.HMAC256(secret));

        assertThrows(
                JWTVerificationException.class,
                () -> jwtService.validateToken(token)
        );
    }

    @Test
    void deveRejeitarTokenComAssinaturaInvalida() {
        String token = JWT.create()
                .withSubject("admin@teste.com")
                .withIssuer("api_pescadores")
                .sign(Algorithm.HMAC256(
                        "outra-chave-secreta-com-tamanho-suficiente-987654"
                ));

        assertThrows(
                JWTVerificationException.class,
                () -> jwtService.validateToken(token)
        );
    }

    @Test
    void deveRejeitarTokenExpirado() {
        String token = JWT.create()
                .withSubject("admin@teste.com")
                .withIssuer("api_pescadores")
                .withExpiresAt(
                        Date.from(Instant.now().minusSeconds(60))
                )
                .sign(Algorithm.HMAC256(secret));

        assertThrows(
                JWTVerificationException.class,
                () -> jwtService.validateToken(token)
        );
    }

}