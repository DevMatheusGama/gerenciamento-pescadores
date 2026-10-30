package dev.matheusGama.gerenciamento_pescadores_api.dto.response;

import jakarta.validation.constraints.NotBlank;

public record EnderecoResponse(
        String municipio,
        String bairro,
        String CEP,
        String logradouroNumero,
        String complemento
) {
}
