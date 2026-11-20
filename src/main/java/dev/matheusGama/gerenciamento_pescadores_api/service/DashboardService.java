package dev.matheusGama.gerenciamento_pescadores_api.service;

import dev.matheusGama.gerenciamento_pescadores_api.dto.response.DashboardResponse;
import dev.matheusGama.gerenciamento_pescadores_api.dto.response.PagamentoMensalResponse;
import dev.matheusGama.gerenciamento_pescadores_api.entity.Parcela;
import dev.matheusGama.gerenciamento_pescadores_api.enums.ParcelaEnum;
import dev.matheusGama.gerenciamento_pescadores_api.repository.ParcelaRepository;
import dev.matheusGama.gerenciamento_pescadores_api.repository.PescadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final PescadorRepository pescadorRepository;
    private final ParcelaRepository parcelaRepository;

    public DashboardResponse dashboard() {

        int totalPescadores = (int) pescadorRepository.count();

        List<Parcela> parcelas = parcelaRepository.findAll();


        List<Parcela> parcelasPagas = parcelas.stream()
                .filter(parcela -> parcela.getDataPagamento() != null)
                .toList();

        List<Parcela> parcelasPendentes = parcelas.stream()
                .filter(parcela -> parcela.getDataPagamento() == null)
                .toList();

        BigDecimal totalArrecadado = parcelasPagas.stream()
                .map(Parcela::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<PagamentoMensalResponse> pagamentoMensalResponses = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {

            int mesAtual = i;

            BigDecimal valorPago = parcelas.stream()
                    .filter(parcela -> parcela.getMes() == mesAtual)
                    .filter(parcela -> parcela.getDataPagamento() != null)
                    .map(Parcela::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal valorPendente = parcelas.stream()
                    .filter(parcela -> parcela.getMes() == mesAtual)
                    .filter(parcela -> parcela.getDataPagamento() == null)
                    .map(Parcela::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            PagamentoMensalResponse response = new PagamentoMensalResponse(
                    ParcelaEnum.obterNomeMes(i),
                    valorPago,
                    valorPendente
            );

            pagamentoMensalResponses.add(response);
        }

        return new DashboardResponse(
                totalPescadores,
                parcelasPagas.size(),
                parcelasPendentes.size(),
                totalArrecadado,
                pagamentoMensalResponses
        );
    }

}
