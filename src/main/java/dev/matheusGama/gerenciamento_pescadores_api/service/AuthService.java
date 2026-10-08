package dev.matheusGama.gerenciamento_pescadores_api.service;

import dev.matheusGama.gerenciamento_pescadores_api.dto.request.LoginRequest;
import dev.matheusGama.gerenciamento_pescadores_api.dto.request.RegisterRequest;
import dev.matheusGama.gerenciamento_pescadores_api.dto.response.RegisterResponse;
import dev.matheusGama.gerenciamento_pescadores_api.entity.Usuario;
import dev.matheusGama.gerenciamento_pescadores_api.repository.UsuarioRepository;
import dev.matheusGama.gerenciamento_pescadores_api.security.CustomUserDetails;
import dev.matheusGama.gerenciamento_pescadores_api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    public RegisterResponse register(RegisterRequest request) {
        String encryptedPassword = passwordEncoder.encode(request.password());

        Usuario usuario = new Usuario();

        usuario.setEmail(request.email());
        usuario.setPassword(encryptedPassword);
        usuario.setRole(request.role());

        usuarioRepository.save(usuario);

        return new RegisterResponse(
                request.email(),
                request.role().name()
        );
    }

    public String login(LoginRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        return jwtService.generateToken(userDetails.getUsuario());
    }
}
