package dev.matheusGama.gerenciamento_pescadores_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PescadorRequest(
        @NotBlank(message = "Nome do pescador é invalido")
        String nome,
        @NotBlank(message = "Cpf do pescador é invalido")
        String cpf,
        @NotBlank(message = "Enderço do pescador é invalido")
        String endereco
) {
}
