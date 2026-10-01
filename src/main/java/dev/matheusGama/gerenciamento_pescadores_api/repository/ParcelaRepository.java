package dev.matheusGama.gerenciamento_pescadores_api.repository;

import dev.matheusGama.gerenciamento_pescadores_api.entity.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParcelaRepository extends JpaRepository<Parcela, UUID> {
}
