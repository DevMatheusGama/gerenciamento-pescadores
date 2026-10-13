package dev.matheusGama.gerenciamento_pescadores_api.exception;

public class ErrorLocatingPescadorByIDException extends RuntimeException {
    public ErrorLocatingPescadorByIDException(String message) {
        super(message);
    }
}
