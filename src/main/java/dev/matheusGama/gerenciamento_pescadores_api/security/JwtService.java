package dev.matheusGama.gerenciamento_pescadores_api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import dev.matheusGama.gerenciamento_pescadores_api.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {
    @Value("${spring.api.secret.secret}")
    private String secret;

    public String generateToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withSubject(usuario.getEmail())
                    .withIssuer("api_pescadores")
                    .withExpiresAt(expireToken())
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token");
        }
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.require(algorithm)
                .withIssuer("api_pescadores")
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant expireToken() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("02:00"));
    }
}
