package dev.matheusGama.gerenciamento_pescadores_api.security;

import dev.matheusGama.gerenciamento_pescadores_api.entity.Usuario;
import dev.matheusGama.gerenciamento_pescadores_api.enums.RoleUser;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustonUserDetails implements UserDetails {
    private Usuario usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (usuario.getRole() == RoleUser.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ADMIN"),
                    new SimpleGrantedAuthority("USER")
            );
        };

        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public @Nullable String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }
}
