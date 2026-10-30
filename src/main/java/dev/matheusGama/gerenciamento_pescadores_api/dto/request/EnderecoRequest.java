package dev.matheusGama.gerenciamento_pescadores_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EnderecoRequest(
        @NotBlank(message = "Municipio do pescador é invalido")
        String municipio,
        @NotBlank(message = "Bairro do pescador é invalido")
        String bairro,
        String cep,
        String logradouroNumero,
        String complemento
) {
}
