package dev.matheusGama.gerenciamento_pescadores_api.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ParcelaResumoResponse(
        UUID parcela_id,
        String nomeMes,
        long mes,
        double valor,
        boolean pago,
        LocalDateTime dataPagamento
) {
}
