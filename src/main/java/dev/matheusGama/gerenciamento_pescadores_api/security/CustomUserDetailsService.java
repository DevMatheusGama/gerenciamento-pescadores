package dev.matheusGama.gerenciamento_pescadores_api.security;

import dev.matheusGama.gerenciamento_pescadores_api.entity.Usuario;
import dev.matheusGama.gerenciamento_pescadores_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Erro ao buscar usuario com email: " + username));

        return new CustonUserDetails(usuario);
    }
}
