package dev.matheusGama.gerenciamento_pescadores_api.enums;

public enum RoleUser {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    RoleUser(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
