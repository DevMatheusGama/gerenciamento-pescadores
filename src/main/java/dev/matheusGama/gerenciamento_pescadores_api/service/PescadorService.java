package dev.matheusGama.gerenciamento_pescadores_api.service;

import dev.matheusGama.gerenciamento_pescadores_api.dto.request.PescadorRequest;
import dev.matheusGama.gerenciamento_pescadores_api.dto.response.ParcelaResumoResponse;
import dev.matheusGama.gerenciamento_pescadores_api.dto.response.PescadorResponse;
import dev.matheusGama.gerenciamento_pescadores_api.entity.Parcela;
import dev.matheusGama.gerenciamento_pescadores_api.entity.Pescador;
import dev.matheusGama.gerenciamento_pescadores_api.enuns.ParcelaEnum;
import dev.matheusGama.gerenciamento_pescadores_api.exception.ErrorLocatingMonthException;
import dev.matheusGama.gerenciamento_pescadores_api.exception.ErrorLocatingPescadorByCpfException;
import dev.matheusGama.gerenciamento_pescadores_api.exception.ErrorLocatingPescadorByIDException;
import dev.matheusGama.gerenciamento_pescadores_api.repository.PescadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PescadorService {
    private final PescadorRepository pescadorRepository;

    private PescadorResponse toResponse(Pescador pescador) {
        List<ParcelaResumoResponse> parcelas = pescador.getParcelas().stream()
                .map(parcela -> new ParcelaResumoResponse(
                        parcela.getId(),
                        parcela.getNomeMes(),
                        parcela.getMes(),
                        parcela.getValor(),
                        parcela.getPago(),
                        parcela.getDataPagamento()
                ))
                .toList();

        return new PescadorResponse(
                pescador.getId(),
                pescador.getNome(),
                pescador.getCpf(),
                pescador.getEndereco(),
                parcelas
        );
    }

    public PescadorResponse createPescador(PescadorRequest request) {
        if (request.nome() == null || request.nome().isBlank()) {
            throw new IllegalArgumentException("Nome invalido.");
        }

        if (request.cpf() == null || request.cpf().isBlank()) {
            throw new IllegalArgumentException("Cpf invalido");
        }

        if (request.endereco() == null || request.endereco().isBlank()) {
            throw new IllegalArgumentException("Endereço invalido.");
        }

        Pescador pescador = new Pescador();

        pescador.setNome(request.nome());
        pescador.setCpf(request.cpf());
        pescador.setEndereco(request.endereco());

        for (int i = 1; i <= 12; i++) {
            Parcela parcela = new Parcela();

            parcela.setNomeMes(ParcelaEnum.obterNomeMes(i));
            parcela.setMes(i);
            parcela.setValor(BigDecimal.valueOf(25));
            parcela.setPago(false);
            parcela.setDataPagamento(null);
            parcela.setPescador(pescador);

            pescador.getParcelas().add(parcela);
        }

        return toResponse(pescadorRepository.save(pescador));
    }

    public List<PescadorResponse> findAllPescadores() {
        return pescadorRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public PescadorResponse findByPescadorId(UUID id) {
        Pescador pescador = pescadorRepository.findById(id)
                .orElseThrow(() -> new ErrorLocatingPescadorByIDException("Erro ao buscar pescador com id: " + id));

        return toResponse(pescador);
    }

    public PescadorResponse findByPescadorCpf(String cpf) {
        Pescador pescador = pescadorRepository.findByCpf(cpf)
                .orElseThrow(() -> new ErrorLocatingPescadorByCpfException("Erro ao buscar pescador com cpf: " + cpf));

        return toResponse(pescador);
    }

    public void deletePescador(UUID id) {
        Pescador pescador = pescadorRepository.findById(id)
                .orElseThrow(() -> new ErrorLocatingPescadorByIDException("Erro ao buscar pescador com id: " + id));

        pescadorRepository.delete(pescador);
    }

    public PescadorResponse updatePescador(PescadorRequest request, UUID id) {
        Pescador pescador = pescadorRepository.findById(id)
                .orElseThrow(() -> new ErrorLocatingPescadorByIDException("Erro ao buscar pescador com id: " + id));

        pescador.setNome(request.nome());
        pescador.setCpf(request.cpf());
        pescador.setEndereco(request.endereco());

        return toResponse(pescadorRepository.save(pescador));
    }

    @Transactional
    public ParcelaResumoResponse pagarParcela(UUID pescador_Id, long mes) {
        Pescador pescador = pescadorRepository.findById(pescador_Id)
                .orElseThrow(() -> new ErrorLocatingPescadorByIDException("Erro ao buscar pescador com id: " + pescador_Id));

        Parcela parcela = pescador.getParcelas().stream()
                .filter(p -> p.getMes() == mes)
                .findFirst()
                .orElseThrow(() ->
                        new ErrorLocatingMonthException("Parcela do mês " + mes + " não encontrada")
                );

        parcela.setPago(true);
        parcela.setDataPagamento(LocalDateTime.now());

        return new ParcelaResumoResponse(
                parcela.getId(),
                parcela.getNomeMes(),
                parcela.getMes(),
                parcela.getValor(),
                parcela.getPago(),
                parcela.getDataPagamento()
        );
    }
}
