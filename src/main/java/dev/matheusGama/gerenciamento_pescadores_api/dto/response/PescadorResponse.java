package dev.matheusGama.gerenciamento_pescadores_api.dto.response;

import java.util.Set;
import java.util.UUID;

public record PescadorResponse(
        UUID pescador_id,
        String nome,
        String endereco,
        Set<ParcelaResumoResponse> parcelas
) {
}
