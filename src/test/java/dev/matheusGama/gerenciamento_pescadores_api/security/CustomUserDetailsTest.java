package dev.matheusGama.gerenciamento_pescadores_api.security;

import dev.matheusGama.gerenciamento_pescadores_api.entity.Usuario;
import dev.matheusGama.gerenciamento_pescadores_api.enums.RoleUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomUserDetailsTest {

    private Usuario usuario;
    private CustomUserDetails customUserDetails;

    @BeforeEach
    void setup() {
        usuario = mock(Usuario.class);

        when(usuario.getEmail()).thenReturn("usuario@gmail.com");
        when(usuario.getPassword()).thenReturn("123");

        customUserDetails = new CustomUserDetails(usuario);
    }

    @Test
    void deveRetornarEmailComoUsername() {
        assertEquals(
                "usuario@gmail.com",
                usuario.getEmail()
        );
    }

    @Test
    void deveRetornarSenhaDoUsuario() {
        assertEquals(
                "123",
                usuario.getPassword()
        );
    }

    @Test
    void deveRetornarDuasAuthoritiesQuandoUsuarioForAdmin() {
        when(usuario.getRole()).thenReturn(RoleUser.ADMIN);

        Set<String> authorities = customUserDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toSet());

        assertEquals(
                Set.of("ROLE_USER", "ROLE_ADMIN"),
                authorities
        );
    }

    @Test
    void deveRetornarSomenteRoleUserQuandoNaoForAdmin() {
        when(usuario.getRole()).thenReturn(RoleUser.USER);

        Set<String> authorities = customUserDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toSet());

        assertEquals(
                Set.of("ROLE_USER"),
                authorities
        );
    }
}