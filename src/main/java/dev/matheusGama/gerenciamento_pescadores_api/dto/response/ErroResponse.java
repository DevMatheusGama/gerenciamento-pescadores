package dev.matheusGama.gerenciamento_pescadores_api.dto.response;

import java.time.LocalDateTime;

public record ErroResponse(
        int status,
        String message,
        String erro,
        LocalDateTime timesTamp
) {
}
