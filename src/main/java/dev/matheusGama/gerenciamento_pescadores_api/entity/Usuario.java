package dev.matheusGama.gerenciamento_pescadores_api.entity;

import dev.matheusGama.gerenciamento_pescadores_api.enums.RoleUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Email
    @Column(unique = true)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleUser role;
}
