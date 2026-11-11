package dev.matheusGama.gerenciamento_pescadores_api.service;

import dev.matheusGama.gerenciamento_pescadores_api.dto.response.UsuarioResponse;
import dev.matheusGama.gerenciamento_pescadores_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> new UsuarioResponse(
                        usuario.getId(),
                        usuario.getName(),
                        usuario.getEmail(),
                        usuario.getRole().toString()
                ))
                .toList();
    }
}
