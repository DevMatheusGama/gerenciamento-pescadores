package dev.matheusGama.gerenciamento_pescadores_api.dto.request;

import dev.matheusGama.gerenciamento_pescadores_api.entity.Endereco;
import jakarta.validation.constraints.NotBlank;

public record PescadorRequest(
        @NotBlank(message = "Nome do pescador é invalido")
        String nome,
        @NotBlank(message = "Cpf do pescador é invalido")
        String cpf,
        @NotBlank(message = "Contato do pescador é invalido")
        String contato,
        String observacao,
        @NotBlank(message = "Enderço do pescador é invalido")
        EnderecoRequest endereco
) {
}
