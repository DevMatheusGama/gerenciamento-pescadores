package dev.matheusGama.gerenciamento_pescadores_api.dto.request;

import dev.matheusGama.gerenciamento_pescadores_api.entity.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PescadorRequest(
        @NotBlank(message = "Nome do pescador é invalido")
        String nome,
        @NotBlank(message = "Cpf do pescador é invalido")
        String cpf,
        @NotBlank(message = "Contato do pescador é invalido")
        String contato,
        String observacao,
        @NotNull(message = "Endereço do pescador é inválido")
        EnderecoRequest endereco
) {
}
