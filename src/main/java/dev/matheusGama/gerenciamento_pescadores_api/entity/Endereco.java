package dev.matheusGama.gerenciamento_pescadores_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String municipio;

    private String bairro;

    private String cep;

    private String logradouroNumero;

    private String complemento;

    @OneToOne
    @JoinColumn(name = "pescador_id")
    private Pescador pescador;

}
