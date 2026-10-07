package dev.matheusGama.gerenciamento_pescadores_api.entity;

import dev.matheusGama.gerenciamento_pescadores_api.enums.RoleUser;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Email
    @Column(unique = true)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleUser role;
}
