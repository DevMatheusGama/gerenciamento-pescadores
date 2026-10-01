package dev.matheusGama.gerenciamento_pescadores_api.service;

import dev.matheusGama.gerenciamento_pescadores_api.entity.Pescador;
import dev.matheusGama.gerenciamento_pescadores_api.repository.ParcelaRepository;
import dev.matheusGama.gerenciamento_pescadores_api.repository.PescadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PescadorService {
    private final PescadorRepository pescadorRepository;
    private final ParcelaRepository parcelaRepository;

    public PescadorResponse savePescador() {

    }
}
