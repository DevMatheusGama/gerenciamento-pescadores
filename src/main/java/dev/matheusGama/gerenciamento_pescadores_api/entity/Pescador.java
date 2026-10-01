package dev.matheusGama.gerenciamento_pescadores_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pescador {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @CPF
    @Column(unique = true)
    private String cpf;

    private String endereco;

    @OneToMany(mappedBy = "pescador", cascade = CascadeType.ALL)
    private Set<Parcela> pareclas = new HashSet<>();
}
