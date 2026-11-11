package dev.matheusGama.gerenciamento_pescadores_api.dto.request;

import dev.matheusGama.gerenciamento_pescadores_api.enums.RoleUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotBlank(message = "Passe um email valido.")
        String email,
        @NotBlank(message = "Passe um password valido.")
        String password,
        @NotBlank(message = "Passe um name valido.")
        String name,
        @NotNull(message = "Passe uma role valido.")
        RoleUser role
) {
}
