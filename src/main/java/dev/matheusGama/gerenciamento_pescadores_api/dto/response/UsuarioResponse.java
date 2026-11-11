package dev.matheusGama.gerenciamento_pescadores_api.dto.response;

import dev.matheusGama.gerenciamento_pescadores_api.enums.RoleUser;

import java.util.Set;
import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String name,
        String email,
        String role
) {}
