package dev.matheusGama.gerenciamento_pescadores_api.dto.request;

import dev.matheusGama.gerenciamento_pescadores_api.enums.RoleUser;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "Passe um email valido.")
        String email,
        @NotBlank(message = "Passe um password valido.")
        String password,
        @NotBlank(message = "Passe uma role valido.")
        RoleUser role
) {
}
