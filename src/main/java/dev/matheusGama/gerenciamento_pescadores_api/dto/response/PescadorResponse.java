package dev.matheusGama.gerenciamento_pescadores_api.dto.response;

import java.util.List;
import java.util.UUID;

public record PescadorResponse(
        UUID pescador_id,
        String nome,
        String cpf,
        String endereco,
        List<ParcelaResumoResponse> parcelas
) {
}
