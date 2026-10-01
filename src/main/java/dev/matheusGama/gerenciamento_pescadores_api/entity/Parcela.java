package dev.matheusGama.gerenciamento_pescadores_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Parcela {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nomeMes;

    private long mes;

    private double valor;

    private Boolean pago;

    private LocalDateTime dataPagamento;

    @ManyToOne
    @JoinColumn(name = "pescador_id")
    private Pescador pescador;
}
