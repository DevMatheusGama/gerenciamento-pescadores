package dev.matheusGama.gerenciamento_pescadores_api.exception;

public class ErrorLocatingPescadorByID extends RuntimeException {
    public ErrorLocatingPescadorByID(String message) {
        super(message);
    }
}
