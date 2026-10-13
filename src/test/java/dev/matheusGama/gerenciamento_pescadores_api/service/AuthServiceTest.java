
package dev.matheusGama.gerenciamento_pescadores_api.service;

import dev.matheusGama.gerenciamento_pescadores_api.dto.request.LoginRequest;
import dev.matheusGama.gerenciamento_pescadores_api.dto.request.RegisterRequest;
import dev.matheusGama.gerenciamento_pescadores_api.entity.Usuario;
import dev.matheusGama.gerenciamento_pescadores_api.enums.RoleUser;
import dev.matheusGama.gerenciamento_pescadores_api.repository.UsuarioRepository;
import dev.matheusGama.gerenciamento_pescadores_api.security.CustomUserDetails;
import dev.matheusGama.gerenciamento_pescadores_api.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void deveRealizarLoginERetornarToken() {
        // Arrange
        LoginRequest request =
                new LoginRequest("teste@email.com", "123456");

        Usuario usuario = new Usuario();

        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        )).thenReturn(authentication);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsuario()).thenReturn(usuario);
        when(jwtService.generateToken(usuario)).thenReturn("jwt-teste");

        // Act
        String token = authService.login(request);

        // Assert
        assertEquals("jwt-teste", token);

        verify(authenticationManager).authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        );

        verify(jwtService).generateToken(usuario);
    }

    @Test
    void deveCadastrarUsuarioComSenhaCriptografada() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                "teste@email.com",
                "123456",
                RoleUser.USER
        );

        when(passwordEncoder.encode("123456"))
                .thenReturn("senha-criptografada");

        // Act
        var response = authService.register(request);

        // Assert
        assertEquals("teste@email.com", response.email());
        assertEquals("USER", response.role());

        var captor = org.mockito.ArgumentCaptor.forClass(Usuario.class);

        verify(usuarioRepository).save(captor.capture());

        Usuario usuarioSalvo = captor.getValue();

        assertEquals("teste@email.com", usuarioSalvo.getEmail());
        assertEquals("senha-criptografada", usuarioSalvo.getPassword());
        assertEquals(RoleUser.USER, usuarioSalvo.getRole());

        verify(passwordEncoder).encode("123456");
    }

    @Test
    void naoDeveGerarTokenQuandoLoginFalhar() {
        // Arrange
        LoginRequest request = new LoginRequest(
                "teste@email.com",
                "senha-incorreta"
        );

        when(authenticationManager.authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        )).thenThrow(new BadCredentialsException("Credenciais inválidas"));

        // Act + Assert
        assertThrows(
                BadCredentialsException.class,
                () -> authService.login(request)
        );

        verify(jwtService, never()).generateToken(any(Usuario.class));
    }

    @Test
    void deveSalvarUsuarioComPerfilAdminQuandoSolicitado() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                "admin@email.com",
                "123456",
                RoleUser.ADMIN
        );

        when(passwordEncoder.encode("123456"))
                .thenReturn("senha-criptografada");

        // Act
        authService.register(request);

        // Assert
        var captor = org.mockito.ArgumentCaptor.forClass(Usuario.class);

        verify(usuarioRepository).save(captor.capture());

        assertEquals(RoleUser.ADMIN, captor.getValue().getRole());
    }
}
