package dev.matheusGama.gerenciamento_pescadores_api.dto.response;

import java.math.BigDecimal;

public record PagamentoMensalResponse(
        String mes,
        BigDecimal  valorPago,
        BigDecimal  valorPendente
) {}
