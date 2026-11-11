package dev.matheusGama.gerenciamento_pescadores_api.controller;

import dev.matheusGama.gerenciamento_pescadores_api.dto.response.UsuarioResponse;
import dev.matheusGama.gerenciamento_pescadores_api.entity.Usuario;
import dev.matheusGama.gerenciamento_pescadores_api.security.CustomUserDetails;
import dev.matheusGama.gerenciamento_pescadores_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> buscarUsuarioLogado(
            @AuthenticationPrincipal CustomUserDetails usuario
    ) {
        if (usuario == null) {
            throw new RuntimeException("Usuário autenticado não encontrado");
        }

        Usuario user = usuario.getUsuario();

        return ResponseEntity.ok(new UsuarioResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().toString()
        ));
    }
}