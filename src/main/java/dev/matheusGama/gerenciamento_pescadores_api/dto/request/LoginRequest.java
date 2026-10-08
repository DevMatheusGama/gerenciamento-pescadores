package dev.matheusGama.gerenciamento_pescadores_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Passe um email valido.")
        String email,
        @NotBlank(message = "Passe um password valido.")
        String password
) {
}
