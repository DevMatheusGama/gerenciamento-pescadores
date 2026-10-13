package dev.matheusGama.gerenciamento_pescadores_api.security;

import dev.matheusGama.gerenciamento_pescadores_api.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class JwtFilterTest {

    private JwtService jwtService;
    private UsuarioRepository usuarioRepository;
    private JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {
        jwtService = mock(JwtService.class);
        usuarioRepository = mock(UsuarioRepository.class);

        jwtFilter = new JwtFilter(jwtService, usuarioRepository);

        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void deveContinuarQuandoNaoExisteCookie()
            throws ServletException, IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        jwtFilter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        verifyNoInteractions(jwtService, usuarioRepository);

        assertNull(
                SecurityContextHolder.getContext().getAuthentication()
        );
    }

    @Test
    void naoDeveAutenticarQuandoTokenForInvalido()
            throws ServletException, IOException {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        request.setCookies(
                new Cookie("access_token", "token-invalido")
        );

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        FilterChain chain = mock(FilterChain.class);

        when(jwtService.validateToken("token-invalido"))
                .thenThrow(
                        new com.auth0.jwt.exceptions.JWTDecodeException(
                                "Token inválido"
                        )
                );

        jwtFilter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);

        assertNull(
                SecurityContextHolder.getContext()
                        .getAuthentication()
        );
    }
}