package dev.matheusGama.gerenciamento_pescadores_api.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponse(
        int totalPescadores,

        int parcelasPagas,
        int parcelasPendentes,

        BigDecimal totalArrecadado,

        List<PagamentoMensalResponse> pagamentosPorMes
) {}
