package dev.matheusGama.gerenciamento_pescadores_api.repository;

import dev.matheusGama.gerenciamento_pescadores_api.entity.Pescador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PescadorRepository extends JpaRepository<Pescador, UUID> {
    Optional<Pescador> findByCpf();
}
